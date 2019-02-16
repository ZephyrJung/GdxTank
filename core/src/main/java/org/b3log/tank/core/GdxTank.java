package org.b3log.tank.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.input.KeyboardProcessor;
import org.b3log.tank.model.Tank;
import org.b3log.tank.model.common.KeyboardInput;
import org.b3log.tank.model.common.Position;

@Slf4j
public class GdxTank implements ApplicationListener {
    private Texture texture;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private float elapsed;
    private KeyboardInput keyboardInput = new KeyboardInput();
    private Position position = Position.of(50, 50);

    @Override
    public void create() {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new KeyboardProcessor(keyboardInput));
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
        log.debug("moveControl:> input:{}, position:{}", keyboardInput, position);
        Tank tank = new Tank(shapeRenderer, position);
        tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
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
            position.setY(position.getY() + 1);
        }
        if (keyboardInput.isDown()) {
            position.setY(position.getY() - 1);
        }
        if (keyboardInput.isLeft()) {
            position.setX(position.getX() - 1);
        }
        if (keyboardInput.isRight()) {
            position.setX(position.getX() + 1);
        }
        if (keyboardInput.isRotateLeft()) {
            position.setAngle(position.getAngle() - 1);
        }
        if (keyboardInput.isRotateRight()) {
            position.setAngle(position.getAngle() + 1);
        }
    }
}
