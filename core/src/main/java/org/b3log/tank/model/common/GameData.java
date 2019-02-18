package org.b3log.tank.model.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:56 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class GameData implements Serializable {
    private String playerId;
    private Position position = new Position();
    private float moveAngle;
    private float rotateAngle;
    private Map<String, Queue<Position>> bullets;

    public static GameData setPosition(String playerId, int x, int y) {
        GameData gameData = new GameData();
        gameData.setPlayerId(playerId);
        gameData.setPosition(Position.of(x, y));
        return gameData;
    }

    @Data
    public static class Position implements Serializable {
        private float x;
        private float y;

        public static Position of(float x, float y) {
            Position position = new Position();
            position.x = x;
            position.y = y;
            return position;
        }
    }
}
