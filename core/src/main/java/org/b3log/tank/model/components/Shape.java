package org.b3log.tank.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.b3log.tank.model.common.Position;

/**
 * @author : yu.zhang
 * Date : 2019/2/15 4:09 PM
 * Email : zephyrjung@126.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Shape {
    protected Position position;
    protected Color color;
    protected ShapeRenderer.ShapeType shapeType;

    public abstract void draw(ShapeRenderer shapeRenderer);
}
