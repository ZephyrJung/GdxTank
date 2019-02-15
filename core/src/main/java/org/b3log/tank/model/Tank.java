package org.b3log.tank.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.b3log.tank.model.common.Position;
import org.b3log.tank.model.components.Circle;
import org.b3log.tank.model.components.Rectangle;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 2:23 PM
 * Email : zephyrjung@126.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tank {
    private ShapeRenderer shapeRenderer;
    private Position position;
//    private int level;
//    private Color color;

    public void draw(Head head, Body body, Weapon weapon) {
        Position headPos = new Position();
        headPos.setX(position.getX());
        headPos.setY(position.getY());
        drawHead(headPos, head.radius, head.segments);
        Position bodyPos = new Position();
        bodyPos.setX(position.getX() - body.width / 2f);
        bodyPos.setY(position.getY() - body.height / 2f);
        drawBody(bodyPos, body.width, body.height);
        Position weaponPos = new Position();
        weaponPos.setX(headPos.getX() - weapon.width / 2f);
        weaponPos.setY(headPos.getY() + head.radius - 3f);
        drawBody(weaponPos, weapon.width, weapon.height);
    }

    private void drawHead(Position position, float radius, int segments) {
        Circle circle = new Circle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(radius);
        circle.setSegments(segments);
        circle.draw(shapeRenderer);
    }

    private void drawBody(Position position, float width, float height) {
        Rectangle rectangle = new Rectangle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.draw(shapeRenderer);
    }

    @Data
    @AllArgsConstructor
    public static class Head {
        private float radius;
        private int segments;
    }

    @Data
    @AllArgsConstructor
    public static class Body {
        private float width;
        private float height;
    }

    @Data
    @AllArgsConstructor
    public static class Weapon {
        private float width;
        private float height;
    }
}
