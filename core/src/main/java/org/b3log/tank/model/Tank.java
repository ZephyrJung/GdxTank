package org.b3log.tank.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.b3log.tank.model.common.GameData;
import org.b3log.tank.model.common.Position;
import org.b3log.tank.model.components.Circle;
import org.b3log.tank.model.components.Rectangle;

import java.util.LinkedList;
import java.util.Queue;

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
    private GameData gameData;
//    private int level;
//    private Color color;

    public void draw(Head head, Body body, Weapon weapon) {
        Position headPos = new Position();
        headPos.setX(gameData.getPosition().getX());
        headPos.setY(gameData.getPosition().getY());
//        headPos.setRotateAngle(gameData.getRotateAngle());
        drawHead(headPos, head.radius, head.segments);

        Position bodyPos = new Position();
        bodyPos.setX(gameData.getPosition().getX() - body.width / 2f);
        bodyPos.setY(gameData.getPosition().getY() - body.height / 2f);
        bodyPos.setMoveAngle(gameData.getPosition().getMoveAngle());
        drawBody(bodyPos, body.width, body.height, gameData.getPosition().getX() - bodyPos.getX(), gameData.getPosition().getY() - bodyPos.getY());

        Position weaponPos = new Position();
        weaponPos.setX(headPos.getX() - weapon.width / 2f);
        weaponPos.setY(headPos.getY() + head.radius - 3f);
        weaponPos.setMoveAngle(gameData.getRotateAngle());
        drawWeapon(weaponPos, weapon.width, weapon.height, gameData.getPosition().getX() - weaponPos.getX(), gameData.getPosition().getY() - weaponPos.getY());

        Queue<Position> fireBalls = new LinkedList<>();
        gameData.getFireBalls().forEach(fire -> {
            drawFire(fire);
        });
    }

    private void drawFire(Position position) {

    }

    private void drawHead(Position position, float radius, int segments) {
        Circle circle = new Circle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(radius);
        circle.setSegments(segments);
        circle.draw(shapeRenderer);
    }

    private void drawBody(Position position, float width, float height, float x, float y) {
        Rectangle rectangle = new Rectangle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.draw(shapeRenderer, x, y, position.getMoveAngle());
    }

    private void drawWeapon(Position position, float width, float height, float x, float y) {
        Rectangle rectangle = new Rectangle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.draw(shapeRenderer, x, y, position.getMoveAngle());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Head {
        private float radius = 16;
        private int segments = 6;
        private double angle;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private float width = 35;
        private float height = 50;
        private double angle;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weapon {
        private float width = 5;
        private float height = 35;
    }
}
