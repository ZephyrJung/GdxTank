package org.b3log.tank.model.common;

import lombok.Data;

/**
 * @author : yu.zhang
 * Date : 2019/2/16 4:20 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class KeyboardInput {
    /**
     * 上
     */
    private boolean up;
    /**
     * 下
     */
    private boolean down;
    /**
     * 左
     */
    private boolean left;
    /**
     * 右
     */
    private boolean right;
    /**
     * 向右旋转
     */
    private boolean rotateRight;
    /**
     * 向左旋转
     */
    private boolean rotateLeft;
    /**
     * 攻击
     */
    private boolean attack;
    /**
     * 装弹
     */
    private boolean reset;
}
