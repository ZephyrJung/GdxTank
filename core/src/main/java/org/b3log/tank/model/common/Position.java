package org.b3log.tank.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yu.zhang
 * Date : 2019/2/22 6:34 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class Position implements Serializable {
    private float x;
    private float y;
    private float moveAngle;

    public static Position of(float x, float y) {
        Position position = new Position();
        position.x = x;
        position.y = y;
        position.moveAngle = 0;
        return position;
    }
}
