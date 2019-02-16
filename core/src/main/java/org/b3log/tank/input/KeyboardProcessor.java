package org.b3log.tank.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.KeyboardInput;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 9:10 AM
 * Email : zephyrjung@126.com
 **/
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class KeyboardProcessor extends InputAdapter {
    private KeyboardInput input;

    @Override
    public boolean keyDown(int keycode) {
        log.debug("KeyCode:{}", keycode);
        if (keycode == Input.Keys.W) {
            input.setUp(true);
        }
        if (keycode == Input.Keys.S) {
            input.setDown(true);
        }
        if (keycode == Input.Keys.A) {
            input.setLeft(true);
        }
        if (keycode == Input.Keys.D) {
            input.setRight(true);
        }
        if (keycode == Input.Keys.Q) {
            input.setRotateLeft(true);
        }
        if (keycode == Input.Keys.E) {
            input.setRotateRight(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W) {
            input.setUp(false);
        }
        if (keycode == Input.Keys.S) {
            input.setDown(false);
        }
        if (keycode == Input.Keys.A) {
            input.setLeft(false);
        }
        if (keycode == Input.Keys.D) {
            input.setRight(false);
        }
        if (keycode == Input.Keys.Q) {
            input.setRotateLeft(false);
        }
        if (keycode == Input.Keys.E) {
            input.setRotateRight(false);
        }
        return true;
    }
}