package com.sakurawald.logic.script;


import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.screen.GameScreen;

@SuppressWarnings("unchecked")
// Raw use of parameterized class is necessary, if not we will fail to reject the script
public class CollisionDestroyeScript<T extends Component> extends ApplicationScript implements PhysicsContactAdapter {

    protected Class<T> classType;
    protected boolean destroySelfObject;
    protected boolean destroyAnotherObject;

    public CollisionDestroyeScript(GameScreen gameScreen, Class<T> anotherObjectClassType, boolean destroySelfObject , boolean destroyAnotherObject) {
        super(gameScreen);
        this.classType = anotherObjectClassType;
        this.destroyAnotherObject = destroyAnotherObject;
        this.destroySelfObject = destroySelfObject;
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        /* Collide with: Specific Object */
        ComponentMapper<T> componentMapper = this.getGameScreen().getSceneLoader().getEngine().getMapper(classType);
        Component component = componentMapper.get(contactEntity);
        if (component != null) {
            Gdx.app.getApplicationLogger().debug("DestroyedByScript (" + classType.getSimpleName() + ")", "Destroy EntityID: " + this.getEntity());

            // Destroy self object ?
            if (this.destroySelfObject) {
                this.getEngine().delete(this.getEntity());
            }

            // Destroyed another object?
            if (this.destroyAnotherObject) {
                this.getEngine().delete(contactEntity);
            }
        }
    }

}
