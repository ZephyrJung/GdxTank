package org.b3log.tank.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.b3log.tank.client.WebSocketClient;
import org.b3log.tank.input.KeyboardProcessor;
import org.b3log.tank.model.Tank;
import org.b3log.tank.model.common.GameData;
import org.b3log.tank.model.common.KeyboardInput;
import org.b3log.tank.model.common.Position;
import org.b3log.tank.model.constants.Level;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GdxTank implements ApplicationListener {
    private MemcachedClient mcc = null;
    private static volatile GdxTank gdxTank;
    private String player = "zephyr" + MathUtils.random(1, 10);
    private Texture texture;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private int elapsed;
    private KeyboardInput keyboardInput = new KeyboardInput();
    private GameData gameData = GameData.setPosition(player, MathUtils.random(10, 200), MathUtils.random(10, 200));
    //    private Map<String, GameData> gameDataMap = new ConcurrentHashMap<>();
    //    private GameClient gameClient = new GameClient("localhost", 8080, null);
//    private GameClient gameClient = new GameClient("hitbug.cn", 80, null);
    private WebSocketClient webSocketClient = new WebSocketClient();
    private Map<Position, Integer> fireBalls = new ConcurrentHashMap<>();
    private Tank tank = null;

    private GdxTank() {
    }

    public static GdxTank getInstance() {
        if (gdxTank == null) {
            synchronized (GdxTank.class) {
                if (gdxTank == null) {
                    gdxTank = new GdxTank();
                }
            }
        }
        return gdxTank;
    }

    @Override
    public void create() {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new KeyboardProcessor(keyboardInput));
//        gameClient.setDaemon(true);
//        gameClient.start();
        tank = new Tank();
        tank.setShapeRenderer(shapeRenderer);
        webSocketClient.setDaemon(true);
        webSocketClient.start();
        try {
            mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            mcc.set("gameData",900,null);
        } catch (Exception e) {
            log.error("memcached client start error,{}", e);
        }
    }

    @Override
    public void resize(int width, int height) {
        log.debug("resize: width:{}, height:{}", width, height);
    }

    @Override
    public void render() {
//        elapsed += Gdx.graphics.getDeltaTime();
        elapsed += 1;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed), 100 + 25 * (float) Math.sin(elapsed));
//        batch.end();
        moveControl();
        fireControl();
//        Tank tank = new Tank(shapeRenderer, position);
//        tank.draw(new Tank.Head(), new Tank.Body(), new Tank.Weapon());
//        gameClient.notifyServer(gameData);
        webSocketClient.notifyServer(gameData);
        drawGameDatas();

    }

    @Override
    public void pause() {
        log.debug("Game Paused");
    }

    @Override
    public void resume() {
        log.debug("Game Resumed");
    }

    @Override
    public void dispose() {
        log.debug("Game Disposed");
        mcc.shutdown();
    }

    private void moveControl() {
        if (keyboardInput.isUp()) {
            gameData.getPosition().setY(gameData.getPosition().getY() + 1 * MathUtils.cosDeg(gameData.getPosition().getMoveAngle()));
            //TODO 预期应该用加法，但是实际移动反向，具体原因未细查
            gameData.getPosition().setX(gameData.getPosition().getX() - 1 * MathUtils.sinDeg(gameData.getPosition().getMoveAngle()));

        }
        if (keyboardInput.isDown()) {
            gameData.getPosition().setY(gameData.getPosition().getY() - 1 * MathUtils.cosDeg(gameData.getPosition().getMoveAngle()));
            gameData.getPosition().setX(gameData.getPosition().getX() + 1 * MathUtils.sinDeg(gameData.getPosition().getMoveAngle()));
        }
        if (keyboardInput.isRotateLeft()) {
            gameData.setRotateAngle(gameData.getRotateAngle() + 1);
        }
        if (keyboardInput.isRotateRight()) {
            gameData.setRotateAngle(gameData.getRotateAngle() - 1);
        }
        if (keyboardInput.isRight()) {
            gameData.getPosition().setMoveAngle(gameData.getPosition().getMoveAngle() - 1);
        }
        if (keyboardInput.isLeft()) {
            gameData.getPosition().setMoveAngle(gameData.getPosition().getMoveAngle() + 1);
        }
    }

    private void fireControl() {
        if (keyboardInput.isAttack()) {
            Position position = Position.of(gameData.getPosition().getX(), gameData.getPosition().getY());
            position.setMoveAngle(gameData.getRotateAngle());
            gameData.getFireBalls().add(position);
        }
        gameData.getFireBalls().forEach(fb -> fireBalls.put(fb, 1));
    }

    private void drawGameDatas() {
        String gameDataText = mcc.get("gameData").toString();
        JSONObject jsonObject = JSON.parseObject(gameDataText);
        for (Map.Entry gameData : jsonObject.entrySet()) {
            tank.setGameData(JSON.parseObject(gameData.getValue().toString(), GameData.class));
            tank.setLevel(Level.D.getValue());
            tank.draw();
            if (!fireBalls.isEmpty()) {
                tank.fire(fireBalls);
            }
            fireBalls.forEach((k, v) -> {
                if (Level.D.getTankInfo().getFire().getRadius() + v + 1 >= Level.D.getTankInfo().getFire().getArea()) {
                    fireBalls.remove(k);
                }
                fireBalls.put(k, v + 1);
            });
        }
    }

    public void updateGameDataMap(String text) {
        mcc.set("gameData", 900, text);
//        gameDataMap.forEach((k, v) -> this.gameDataMap.put(String.valueOf(k), JSON.parseObject(v.toString(), GameData.class)));
    }
}
