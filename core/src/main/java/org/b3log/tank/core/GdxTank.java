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
import org.b3log.tank.model.common.GameData;
import org.b3log.tank.model.common.KeyboardInput;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GdxTank implements ApplicationListener {
    private static volatile GdxTank gdxTank;
    private String player = "zephyr" + MathUtils.random(1, 10);
    private Texture texture;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private float elapsed;
    private KeyboardInput keyboardInput = new KeyboardInput();
    private GameData gameData = GameData.setPosition(player, MathUtils.random(10, 60), MathUtils.random(50, 100));
    private Map<String, GameData> gameDataMap = new ConcurrentHashMap<>();
    private GameClient gameClient = new GameClient("localhost", 8080, null);

    private GdxTank() {
    }

    public static GdxTank getInstance() {
        if (gdxTank == null) {
            synchronized (GdxTank.class) {
                if (gdxTank == null) {
                    gdxTank = new GdxTank();
                }
            }
        }
        return gdxTank;
    }

    @Override
    public void create() {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new KeyboardProcessor(keyboardInput));
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
//        Tank tank = new Tank(shapeRenderer, gameData);
//        tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
        drawGameDatas();
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
            gameData.setY(gameData.getY() + 1 * MathUtils.cosDeg(gameData.getMoveAngle()));
            //TODO 预期应该用加法，但是实际移动反向，具体原因未细查
            gameData.setX(gameData.getX() - 1 * MathUtils.sinDeg(gameData.getMoveAngle()));

        }
        if (keyboardInput.isDown()) {
            gameData.setY(gameData.getY() - 1 * MathUtils.cosDeg(gameData.getMoveAngle()));
            gameData.setX(gameData.getX() + 1 * MathUtils.sinDeg(gameData.getMoveAngle()));
        }
        if (keyboardInput.isRotateLeft()) {
            gameData.setRotateAngle(gameData.getRotateAngle() + 1);
        }
        if (keyboardInput.isRotateRight()) {
            gameData.setRotateAngle(gameData.getRotateAngle() - 1);
        }
        if (keyboardInput.isRight()) {
            gameData.setMoveAngle(gameData.getMoveAngle() - 1);
        }
        if (keyboardInput.isLeft()) {
            gameData.setMoveAngle(gameData.getMoveAngle() + 1);
        }
        //todo 考虑用切面实现动态通知
        gameClient.notifyServer(gameData);
    }

    private void drawGameDatas() {
        for (Map.Entry<String, GameData> positionMap : gameDataMap.entrySet()) {
            Tank tank = new Tank(shapeRenderer, positionMap.getValue());
            tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
        }
    }

    public void updateGameDataMap(Map<String, GameData> gameDataMap) {
        this.gameDataMap = gameDataMap;
    }
}
