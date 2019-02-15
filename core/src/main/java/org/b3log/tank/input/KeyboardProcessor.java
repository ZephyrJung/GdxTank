package org.b3log.tank.input;

import com.badlogic.gdx.InputAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 9:10 AM
 * Email : zephyrjung@126.com
 **/
@Slf4j
public class KeyboardProcessor extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        log.debug("KeyCode:{}", keycode);
        return true;
    }
}