package com.sakurawald.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sakurawald.data.Point;
import lombok.Data;

@Data
public abstract class ZincShape {
    protected Point position;

    public ZincShape(Point position) {
        this.position = position;
    }

    /** update the data of shape. */
    public abstract void update();


    /** render the date of the shape in the graphics output device. */
    public abstract void draw(ShapeRenderer shapeRenderer);
}
