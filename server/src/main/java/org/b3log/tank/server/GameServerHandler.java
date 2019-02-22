package org.b3log.tank.server;

import com.badlogic.gdx.math.MathUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

import java.util.Map;

@Slf4j
public class GameServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof GameData) {
//            forTest();
            GameData gameData = (GameData) obj;
            GameServer.GAME_DATA_MAP.put(gameData.getPlayerId(), gameData);
            ctx.writeAndFlush(GameServer.GAME_DATA_MAP);
            log.debug("From Client:> {}", gameData);
        }
    }

    public void forTest() {
        GameServer.GAME_DATA_MAP.put("test1", GameData.setPosition("test1", 100, 100));
        GameServer.GAME_DATA_MAP.put("test2", GameData.setPosition("test2", 200, 200));
        GameServer.GAME_DATA_MAP.put("test3", GameData.setPosition("test3", 300, 300));
        GameServer.GAME_DATA_MAP.put("test4", GameData.setPosition("test4", 150, 200));
        GameServer.GAME_DATA_MAP.put("test5", GameData.setPosition("test5", 250, 150));

        for (Map.Entry<String, GameData> positionMap : GameServer.GAME_DATA_MAP.entrySet()) {
            if (positionMap.getKey().equalsIgnoreCase("zephyr")) {
                continue;
            }
            GameData gameData = positionMap.getValue();
            gameData.getPosition().setX(gameData.getPosition().getX() + MathUtils.random(-5, 5));
            gameData.getPosition().setY(gameData.getPosition().getY() + MathUtils.random(-5, 5));
            gameData.setRotateAngle(MathUtils.random(-10, 10));
            gameData.getPosition().setMoveAngle(MathUtils.random(-10, 10));
        }
    }
}
