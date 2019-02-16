package org.b3log.tank.model.common;

import lombok.Data;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:56 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class Position {
    private float x;
    private float y;
    private float moveAngle;
    private float rotateAngle;

    public static Position of(int x, int y) {
        Position position = new Position();
        position.setX(x);
        position.setY(y);
        return position;
    }
}
