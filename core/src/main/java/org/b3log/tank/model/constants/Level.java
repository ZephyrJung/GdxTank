package org.b3log.tank.model.constants;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 4:03 PM
 * Email : zephyrjung@126.com
 **/
public enum Level {
    SSR(1),
    SR(2),
    S(3),
    A(4),
    B(5),
    C(6),
    D(7),
    ;
    private int level;

    Level(int level) {
        this.level = level;
    }
}
