package org.b3log.tank.server;

import com.badlogic.gdx.math.MathUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SerializationServerHandler extends SimpleChannelInboundHandler<Object> {
    private Map<String, GameData> gameDataMap = new ConcurrentHashMap<String, GameData>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        if (obj instanceof GameData) {
            forTest();
            GameData gameData = (GameData) obj;
            gameDataMap.put(gameData.getPlayerId(), gameData);
            ctx.writeAndFlush(gameDataMap);
            log.debug("From Client:> {}", gameData);
        }
    }

    public void forTest() {
        gameDataMap.put("test1", GameData.setPosition("test1", 100, 100));
        gameDataMap.put("test2", GameData.setPosition("test2", 200, 200));
        gameDataMap.put("test3", GameData.setPosition("test3", 300, 300));
        gameDataMap.put("test4", GameData.setPosition("test4", 150, 200));
        gameDataMap.put("test5", GameData.setPosition("test5", 250, 150));

        for (Map.Entry<String, GameData> positionMap : gameDataMap.entrySet()) {
            if (positionMap.getKey().equalsIgnoreCase("zephyr")) {
                continue;
            }
            GameData gameData = positionMap.getValue();
            gameData.setX(gameData.getX() + MathUtils.random(-5, 5));
            gameData.setY(gameData.getY() + MathUtils.random(-5, 5));
            gameData.setRotateAngle(MathUtils.random(-10, 10));
            gameData.setMoveAngle(MathUtils.random(-10, 10));
        }
    }
}
