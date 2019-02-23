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
import org.b3log.tank.model.constants.Level;

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
    private Integer level = 0;
//    private Color color;

    public void draw() {
        TankInfo tankInfo = Level.fromCode(level).getTankInfo();
        Position headPos = gameData.getPosition();
        drawHead(tankInfo.getHead(), headPos);

        Position bodyPos = Position.of(gameData.getPosition().getX() - tankInfo.getBody().getWidth() / 2f,
                gameData.getPosition().getY() - tankInfo.getBody().getHeight() / 2f);
        bodyPos.setMoveAngle(gameData.getPosition().getMoveAngle());
        drawBody(tankInfo.getBody(), bodyPos);

        TankInfo.Weapon weapon = new TankInfo.Weapon();
        Position weaponPos = Position.of(
                headPos.getX() - weapon.getWidth() / 2f,
                headPos.getY() + tankInfo.getHead().getRadius() - 3f
        );
        weaponPos.setMoveAngle(gameData.getRotateAngle());
        drawWeapon(weapon, weaponPos, tankInfo.getHead().getRadius());
    }

    public void fire() {
//        gameData.getFireBalls().forEach((Position fb) -> {
//            TankInfo.Fire fire = new TankInfo.Fire();
//            drawFire(fire);
//        });
    }

    private void drawFire(TankInfo.Fire fire, Position position) {
        Circle circle = new Circle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(fire.getRadius());
        circle.draw(shapeRenderer);
    }

    private void drawHead(TankInfo.Head head, Position position) {
        Circle circle = new Circle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        circle.setRadius(head.getRadius());
        circle.setSegments(head.getSegments());
        circle.draw(shapeRenderer);
    }

    private void drawBody(TankInfo.Body body, Position position) {
        Rectangle rectangle = new Rectangle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(body.getWidth());
        rectangle.setHeight(body.getHeight());
        rectangle.draw(shapeRenderer, body.getWidth() / 2f, body.getHeight() / 2f, position.getMoveAngle());
    }

    private void drawWeapon(TankInfo.Weapon weapon, Position position, float radius) {
        Rectangle rectangle = new Rectangle(position, Color.WHITE, ShapeRenderer.ShapeType.Line);
        rectangle.setWidth(weapon.getWidth());
        rectangle.setHeight(weapon.getHeight());
        rectangle.draw(shapeRenderer, weapon.getWidth() / 2f, 3f - radius, position.getMoveAngle());
    }
}
