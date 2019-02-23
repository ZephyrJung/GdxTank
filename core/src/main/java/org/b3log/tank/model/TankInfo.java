package org.b3log.tank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yu.zhang
 * Date : 2019/2/23 4:24 PM
 * Email : zephyrjung@126.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TankInfo {
    private Head head = new Head();
    private Body body = new Body();
    private Weapon weapon = new Weapon();
    private Fire fire = new Fire();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Head {
        //Color
        private float radius = 16;
        private int segments = 6;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private float width = 35;
        private float height = 50;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weapon {
        private float width = 5;
        private float height = 35;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fire {
        private float radius = 5;
        /**
         * 射程
         */
        private int length;
    }
}
