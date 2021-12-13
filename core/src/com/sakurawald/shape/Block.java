package com.sakurawald.shape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sakurawald.data.Dimension;
import com.sakurawald.data.Point;

import java.util.ArrayList;

public class Block extends ZincShape {

    Dimension dimension;
    Color color;

    public Block(Point position, Dimension dimension, Color color) {
        super(position);
        this.dimension = dimension;
        this.color = color;
    }

    @Override
    public void update() {

    }

    public void checkCollision(Block block) {

    }



    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.getX(), position.getY(), dimension.width, dimension.height);
    }

    public static ArrayList<Block> createBlocks(int blockWidth, int blockHeight, Color color) {
        ArrayList<Block> blocks = new ArrayList<>();

        for (int y = Gdx.graphics.getHeight() / 2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(new Point(x, y), new Dimension(blockWidth, blockHeight), color));
            }
        }

        return blocks;
    }

}
