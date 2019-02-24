package org.b3log.tank.model.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:56 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class GameData implements Serializable {
    private Integer level = 0;
    private String playerId;
    private Position position = new Position();
    private Float rotateAngle = 0f;
    private Queue<Position> fireBalls = new LinkedList<>();
//    private Map<Position,Integer> fireBalls = new HashMap<>();

    public static GameData setPosition(String playerId, int x, int y) {
        GameData gameData = new GameData();
        gameData.setPlayerId(playerId);
        gameData.setPosition(Position.of(x, y));
        return gameData;
    }
}
