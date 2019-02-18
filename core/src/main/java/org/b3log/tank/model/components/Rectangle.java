package org.b3log.tank.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:52 PM
 * Email : zephyrjung@126.com
 **/
@Data
@Slf4j
public class Rectangle extends Shape {
    private float width;
    private float height;

    public Rectangle(GameData gameData, Color color, ShapeRenderer.ShapeType shapeType) {
        super(gameData, color, shapeType);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        shapeRenderer.rect(gameData.getX(), gameData.getY(), width, height);
        shapeRenderer.end();
    }

    public void draw(ShapeRenderer shapeRenderer, float x, float y, float angle) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        shapeRenderer.rect(gameData.getX(), gameData.getY(), width, height, x, y, angle);
        shapeRenderer.end();
    }
}
