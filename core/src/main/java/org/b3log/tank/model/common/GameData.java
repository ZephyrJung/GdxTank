package org.b3log.tank.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:56 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class GameData implements Serializable {
    private String playerId;
    private float x;
    private float y;
    private float moveAngle;
    private float rotateAngle;

    public static GameData setPosition(String playerId, int x, int y) {
        GameData gameData = new GameData();
        gameData.setPlayerId(playerId);
        gameData.setX(x);
        gameData.setY(y);
        return gameData;
    }
}
