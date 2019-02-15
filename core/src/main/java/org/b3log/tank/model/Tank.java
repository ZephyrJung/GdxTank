package org.b3log.tank.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Data;
import org.b3log.tank.model.common.Position;
import org.b3log.tank.model.components.Circle;
import org.b3log.tank.model.components.Rectangle;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 2:23 PM
 * Email : zephyrjung@126.com
 **/
@Data
public class Tank {
    private int x;
    private int y;
    private int radius;
//    private int level;
//    private Color color;

    public Tank(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        Circle circle = new Circle(Position.of(x, y), Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(radius);
        circle.setSegments(6);
        circle.draw(shapeRenderer);

        Rectangle rectangle = new Rectangle(Position.of(x, y), Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(100);
        rectangle.setHeight(200);
        rectangle.draw(shapeRenderer);
    }
}
