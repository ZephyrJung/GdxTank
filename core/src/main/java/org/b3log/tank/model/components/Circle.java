package org.b3log.tank.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.Position;

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

    public Circle(Position position, Color color, ShapeRenderer.ShapeType shapeType) {
        super(position, color, shapeType);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        log.debug("Circle Position:{}", position);
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        if (this.segments < 0) {
            shapeRenderer.circle(position.getX(), position.getY(), radius);
        } else {
            shapeRenderer.circle(position.getX(), position.getY(), radius, segments);
        }
        shapeRenderer.end();
    }
}
