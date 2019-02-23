package org.b3log.tank.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.b3log.tank.model.common.GameData;
import org.b3log.tank.model.common.Position;
import org.b3log.tank.model.components.Circle;
import org.b3log.tank.model.components.Rectangle;

import java.util.Map;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 2:23 PM
 * Email : zephyrjung@126.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Tank {
    private ShapeRenderer shapeRenderer;
    private GameData gameData;
//    private int level;
//    private Color color;

    public void draw() {
        Head head = new Head();
        head.setPosition(gameData.getPosition());
        drawHead(head);

        Body body = new Body();
        body.position.setX(gameData.getPosition().getX() - body.width / 2f);
        body.position.setY(gameData.getPosition().getY() - body.height / 2f);
        body.position.setMoveAngle(gameData.getPosition().getMoveAngle());
        drawBody(body);

        Weapon weapon = new Weapon();
        weapon.position.setX(head.position.getX() - weapon.width / 2f);
        weapon.position.setY(head.position.getY() + head.radius - 3f);
        weapon.position.setMoveAngle(gameData.getRotateAngle());
        drawWeapon(weapon, head.radius);

//        gameData.getFireBalls().forEach(fire -> {
//            drawFire(fire);
//        });
    }

    private void drawFire(Position position) {
//        Circle circle = new Circle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
//        circle.setRadius(radius);
//        circle.setSegments(segments);
//        circle.draw(shapeRenderer);
    }

    private void drawHead(Head head) {
        Circle circle = new Circle(head.position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(head.radius);
        circle.setSegments(head.segments);
        circle.draw(shapeRenderer);
    }

    private void drawBody(Body body) {
        Rectangle rectangle = new Rectangle(body.position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(body.width);
        rectangle.setHeight(body.height);
        rectangle.draw(shapeRenderer, body.width / 2f, body.height / 2f, body.position.getMoveAngle());
    }

    private void drawWeapon(Weapon weapon, float radius) {
        Rectangle rectangle = new Rectangle(weapon.position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(weapon.width);
        rectangle.setHeight(weapon.height);
        rectangle.draw(shapeRenderer, weapon.width / 2f, 3f - radius, weapon.position.getMoveAngle());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Head {
        private float radius = 16;
        private int segments = 6;
        private Position position = new Position();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private float width = 35;
        private float height = 50;
        private Position position = new Position();

        Body(Position position) {
            this.position = position;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weapon {
        private float width = 5;
        private float height = 35;
        private Position position = new Position();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fire {
        private float radius = 5;
        private Map<Position, Integer> fireBall;
        /**
         * 射程
         */
        private float length = 20;
    }
}
