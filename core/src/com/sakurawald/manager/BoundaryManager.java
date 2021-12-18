package com.sakurawald.manager;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.logic.entity.Tags;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import lombok.Getter;

import java.util.ArrayList;

@SuppressWarnings("SuspiciousNameCombination")
public class BoundaryManager {

    public static final Float BOUNDARY_THICKNESS = 0.01f;
    public static final Float BOUNDARY_FLAG = 2048.001f;

    @Getter
    private final GameScreen gameScreen;

    @Getter
    private final ArrayList<Shape> boundaryShapes = new ArrayList<>();

    public BoundaryManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void createPolygonBoundary(ArrayList<Vector2> points) {

        /* BodyDef */
        BodyDef boundaryBodyDef = new BodyDef();
        boundaryBodyDef.type = BodyDef.BodyType.StaticBody;

        /* Foreach all the edges */
        for (int i = 0; i < points.size(); i++) {
            Vector2 startVector = points.get(i);
            Vector2 endVector = points.get((i + 1) % points.size());

            Vector2 directionVector = new Vector2(endVector.x - startVector.x, endVector.y - startVector.y);
            Vector2 normalVector = new Vector2(directionVector.y, -directionVector.x);
            normalVector.set(-BOUNDARY_THICKNESS, -BOUNDARY_THICKNESS);

            Vector2 startMovedVector = new Vector2(startVector.x + normalVector.x, startVector.y + normalVector.y);
            Vector2 endMovedVector = new Vector2(endVector.x + normalVector.x, endVector.y + normalVector.y);

            boundaryBodyDef.gravityScale = BoundaryManager.BOUNDARY_FLAG;
            boundaryBodyDef.linearDamping = 0.233f;

            Body boundaryBody = gameScreen.getSceneLoader().getWorld().createBody(boundaryBodyDef);

            System.out.printf("boundaryBody address after construct: %d\n", Box2DUtils.getAddr(boundaryBody));

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vector2[]{
                    startVector,
                    endVector,
                    endMovedVector,
                    startMovedVector
            });

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = BOUNDARY_FLAG;
            Fixture fixture = boundaryBody.createFixture(fixtureDef);

            fixture.setDensity(1.233f);


            System.out.printf("boundaryBody address after create fixture: %d\n", Box2DUtils.getAddr(boundaryBody));

            // Add boundary body instance (Must get the address after all the operations)
            this.boundaryShapes.add(polygonShape);

            // Set UserData
            boundaryBody.setUserData(Tags.BOUNDARY);
        }

    }

    @Deprecated
    public void registerECS() {

        // Call ECS to process.
        this.gameScreen.getSceneLoader().getEngine().process();

        // Get all the PhysicsBodyComponents
        ComponentMapper<PhysicsBodyComponent> mapper = gameScreen.getSceneLoader().getEngine().getMapper(PhysicsBodyComponent.class);
        IntBag entities = getGameScreen().getSceneLoader().getEngine().getAspectSubscriptionManager().get(Aspect.all(PhysicsBodyComponent.class)).getEntities();
        System.out.println("IntBag Entities: " + entities.size());

        // Do filter and Register to ECS
        for (int i = 0; i < entities.size(); i++) {
            int entityID = entities.get(i);
            System.out.println("Registering to ECS foreach: entityID = " + entityID);
            Body body = mapper.get(entityID).body;

            // Register to ECS
            if (boundaryShapes.contains(body)) {
                System.out.println("add boundary ========");
                gameScreen.getSceneLoader().getEngine().edit(entityID).add(new BoundaryComponent());
            }
        }
    }
}
