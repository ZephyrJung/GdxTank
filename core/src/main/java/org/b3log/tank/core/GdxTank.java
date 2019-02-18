package org.b3log.tank.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.client.GameClient;
import org.b3log.tank.input.KeyboardProcessor;
import org.b3log.tank.model.Tank;
import org.b3log.tank.model.common.KeyboardInput;
import org.b3log.tank.model.common.Position;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GdxTank implements ApplicationListener {
    private String player = "zephyr";
    private Texture texture;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private float elapsed;
    private KeyboardInput keyboardInput = new KeyboardInput();
    private Position position = Position.of(50, 50);
    private Map<String, Position> positions = new ConcurrentHashMap<>();
    private GameClient gameClient = new GameClient("localhost", 8080, null);

    @Override
    public void create() {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new KeyboardProcessor(keyboardInput));
        positions.put("test1", Position.of(100, 100));
        positions.put("test2", Position.of(200, 200));
        positions.put("test3", Position.of(300, 300));
        positions.put("test4", Position.of(150, 200));
        positions.put("test5", Position.of(250, 150));
        gameClient.setDaemon(true);
        gameClient.start();
    }

    @Override
    public void resize(int width, int height) {
        log.debug("resize: width:{}, height:{}", width, height);
    }

    @Override
    public void render() {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed), 100 + 25 * (float) Math.sin(elapsed));
//        batch.end();
        moveControl();
        Tank tank = new Tank(shapeRenderer, position);
        tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
        updatePositions(positions);
        gameClient.notifyServer(position);
    }

    @Override
    public void pause() {
        log.debug("Game Paused");
    }

    @Override
    public void resume() {
        log.debug("Game Resumed");
    }

    @Override
    public void dispose() {
        log.debug("Game Disposed");
    }

    private void moveControl() {
        if (keyboardInput.isUp()) {
            position.setY(position.getY() + 1 * MathUtils.cosDeg(position.getMoveAngle()));
            //TODO 预期应该用加法，但是实际移动反向，具体原因未细查
            position.setX(position.getX() - 1 * MathUtils.sinDeg(position.getMoveAngle()));

        }
        if (keyboardInput.isDown()) {
            position.setY(position.getY() - 1 * MathUtils.cosDeg(position.getMoveAngle()));
            position.setX(position.getX() + 1 * MathUtils.sinDeg(position.getMoveAngle()));
        }
        if (keyboardInput.isRotateLeft()) {
            position.setRotateAngle(position.getRotateAngle() + 1);
        }
        if (keyboardInput.isRotateRight()) {
            position.setRotateAngle(position.getRotateAngle() - 1);
        }
        if (keyboardInput.isRight()) {
            position.setMoveAngle(position.getMoveAngle() - 1);
        }
        if (keyboardInput.isLeft()) {
            position.setMoveAngle(position.getMoveAngle() + 1);
        }
    }

    private void updatePositions(Map<String, Position> positions) {
        for (Map.Entry<String, Position> positionMap : positions.entrySet()) {
            Position position = positionMap.getValue();
            position.setX(position.getX() + MathUtils.random(-5, 5));
            position.setY(position.getY() + MathUtils.random(-5, 5));
            position.setRotateAngle(MathUtils.random(-10, 10));
            position.setMoveAngle(MathUtils.random(-10, 10));
            Tank tank = new Tank(shapeRenderer, position);
            tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
        }
    }
}
