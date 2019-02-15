package org.b3log.tank.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Data;
import org.b3log.tank.model.common.Position;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 3:53 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class Circle extends Shape {

    private float radius;
    private int segments;

    public Circle(Position position, Color color, ShapeRenderer.ShapeType shapeType) {
        super(position, color, shapeType);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
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
