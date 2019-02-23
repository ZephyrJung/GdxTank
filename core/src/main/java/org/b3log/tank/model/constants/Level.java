package org.b3log.tank.model.constants;

import org.b3log.tank.model.TankInfo;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 4:03 PM
 * Email : zephyrjung@126.com
 **/
public enum Level {
    SSR(1) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    SR(2) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    S(3) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    A(4) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    B(5) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    C(6) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    D(7) {
        @Override
        public TankInfo getTankInfo() {
            return super.getTankInfo();
        }
    },
    ;
    private int level;

    Level(int level) {
        this.level = level;
    }

    public int getValue() {
        return this.level;
    }

    public static Level fromCode(int level) {
        for (Level l : Level.values()) {
            if (l.level == level) {
                return l;
            }
        }
        return D;
    }

    public TankInfo getTankInfo() {
        return new TankInfo();
    }
}
