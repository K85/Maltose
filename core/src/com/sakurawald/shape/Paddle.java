package com.sakurawald.shape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sakurawald.data.Dimension;
import com.sakurawald.data.Point;

public class Paddle extends ZincShape {

    Dimension dimension;
    Color color;

    public Paddle(Point position, Dimension dimension, Color color) {
        super(position);
        this.dimension = dimension;
        this.color = color;
    }

    @Override
    public void update() {
        getPosition().setX(Gdx.input.getX());
        getPosition().setY(Gdx.graphics.getHeight() - Gdx.input.getY());
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(getPosition().getX(), getPosition().getY(), dimension.width, dimension.height);
    }
}
