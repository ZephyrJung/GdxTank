package org.b3log.tank.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.b3log.tank.model.common.GameData;
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
    private GameData gameData;
//    private int level;
//    private Color color;

    public void draw(Head head, Body body, Weapon weapon) {
        GameData headPos = new GameData();
        headPos.getPosition().setX(gameData.getPosition().getX());
        headPos.getPosition().setY(gameData.getPosition().getY());
        headPos.setRotateAngle(gameData.getRotateAngle());
        drawHead(headPos, head.radius, head.segments);

        GameData bodyPos = new GameData();
        bodyPos.getPosition().setX(gameData.getPosition().getX() - body.width / 2f);
        bodyPos.getPosition().setY(gameData.getPosition().getY() - body.height / 2f);
        bodyPos.setMoveAngle(gameData.getMoveAngle());
        drawBody(bodyPos, body.width, body.height, gameData.getPosition().getX() - bodyPos.getPosition().getX(), gameData.getPosition().getY() - bodyPos.getPosition().getY());

        GameData weaponPos = new GameData();
        weaponPos.getPosition().setX(headPos.getPosition().getX() - weapon.width / 2f);
        weaponPos.getPosition().setY(headPos.getPosition().getY() + head.radius - 3f);
        weaponPos.setRotateAngle(gameData.getRotateAngle());
        drawWeapon(weaponPos, weapon.width, weapon.height, gameData.getPosition().getX() - weaponPos.getPosition().getX(), gameData.getPosition().getY() - weaponPos.getPosition().getY());
    }

    private void drawHead(GameData gameData, float radius, int segments) {
        Circle circle = new Circle(gameData, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(radius);
        circle.setSegments(segments);
        circle.draw(shapeRenderer);
    }

    private void drawBody(GameData gameData, float width, float height, float x, float y) {
        Rectangle rectangle = new Rectangle(gameData, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.draw(shapeRenderer, x, y, gameData.getMoveAngle());
    }

    private void drawWeapon(GameData gameData, float width, float height, float x, float y) {
        Rectangle rectangle = new Rectangle(gameData, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.draw(shapeRenderer, x, y, gameData.getRotateAngle());
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
