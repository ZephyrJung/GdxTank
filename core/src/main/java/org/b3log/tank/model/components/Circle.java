package org.b3log.tank.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:53 PM
 * Email : zephyrjung@126.com
 **/
@Data
@Slf4j
public class Circle extends Shape {

    private float radius;
    private int segments;

    public Circle(GameData gameData, Color color, ShapeRenderer.ShapeType shapeType) {
        super(gameData, color, shapeType);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        if (this.segments < 0) {
            shapeRenderer.circle(gameData.getX(), gameData.getY(), radius);
        } else {
            shapeRenderer.circle(gameData.getX(), gameData.getY(), radius, segments);
        }
        shapeRenderer.end();
    }
}
