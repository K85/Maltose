package com.sakurawald.logic.system;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;

import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.ViewPortComponent;
import lombok.Setter;

@All(ViewPortComponent.class)
public class CameraSystem extends IteratingSystem {

    /* Component Mappers */
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<ViewPortComponent> viewportMapper;

    @Setter
    private int focusEntityID = -1;
    private final float xMin, xMax, yMin, yMax;

    public CameraSystem(float xMin, float xMax, float yMin, float yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    @Override
    protected void process(int entity) {
        ViewPortComponent viewPortComponent = viewportMapper.get(entity);
        Camera camera = viewPortComponent.viewPort.getCamera();

        if (focusEntityID != -1) {
            TransformComponent transformComponent = transformMapper.get(focusEntityID);
            if (transformComponent != null) {
                float x = Math.max(xMin, Math.min(xMax, transformComponent.x));
                float y = Math.max(yMin, Math.min(yMax, transformComponent.y));
                camera.position.set(x, y, 0);
            }
        }
    }

}
