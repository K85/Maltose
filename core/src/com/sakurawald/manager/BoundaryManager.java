package com.sakurawald.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.data.PhysicsBodyDataVO;
import games.rednblack.editor.renderer.data.PolygonShapeVO;
import lombok.Getter;

import java.util.ArrayList;

@SuppressWarnings({"SuspiciousNameCombination", "ClassCanBeRecord"})
public class BoundaryManager {

    private static final Float BOUNDARY_THICKNESS = 0.01f;


    public final float boundaryScale;
    public float boundaryRestitution = 0.5f;

    @Getter
    private final GameScreen gameScreen;

    public BoundaryManager(GameScreen gameScreen, float boundaryScale) {
        this.gameScreen = gameScreen;
        this.boundaryScale = boundaryScale;
    }

    public BoundaryManager(GameScreen gameScreen) {
        this(gameScreen, 1f);
    }

    public void createPolygonBoundary(ArrayList<Vector2> points) {

        /* Foreach all the edges */
        for (int i = 0; i < points.size(); i++) {

            /* Calculate the shape */
            Vector2 startVector = points.get(i);
            Vector2 endVector = points.get((i + 1) % points.size());

            Vector2 directionVector = new Vector2(endVector.x - startVector.x, endVector.y - startVector.y);
            Vector2 normalVector = new Vector2(directionVector.y, -directionVector.x);
            normalVector.set(-BOUNDARY_THICKNESS, -BOUNDARY_THICKNESS);

            Vector2 startMovedVector = new Vector2(startVector.x + normalVector.x, startVector.y + normalVector.y);
            Vector2 endMovedVector = new Vector2(endVector.x + normalVector.x, endVector.y + normalVector.y);

            PolygonShape polygonShape = new PolygonShape();
            Vector2[] vector2s = {
                    startVector.scl(boundaryScale),
                    endVector.scl(boundaryScale),
                    endMovedVector.scl(boundaryScale),
                    startMovedVector.scl(boundaryScale)
            };
            polygonShape.set(vector2s);

            /* Define the Composite */
            CompositeItemVO placeholderComposite = new CompositeItemVO();
            // all the Boundary Objects are located in (0,0)
            placeholderComposite.layerName = "Default";
            placeholderComposite.x = 0;
            placeholderComposite.y = 0;

            PhysicsBodyDataVO physicsBodyDataVO = new PhysicsBodyDataVO();
            physicsBodyDataVO.bodyType = BodyDef.BodyType.StaticBody.getValue();
            physicsBodyDataVO.restitution = this.boundaryRestitution;
            placeholderComposite.physics = physicsBodyDataVO;

            PolygonShapeVO polygonShapeVO = new PolygonShapeVO();
            polygonShapeVO.polygons = new Vector2[][]{vector2s};
            placeholderComposite.shape = polygonShapeVO;

            /* Create the Entity */
            ApplicationAssetManager.createEntityFromCompositeVO(getGameScreen().getSceneLoader(),
                    placeholderComposite, new ArrayList<>() {
                        {
                            this.add(BoundaryComponent.class);
                        }
                    });
        }
    }

}
