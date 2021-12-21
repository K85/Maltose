package com.sakurawald.logic.script;


import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.screen.GameScreen;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public class DestroyedByScript<T extends Component> extends ApplicationScript implements PhysicsContactAdapter {

    /* Field */
    protected Class<T> classType;

    /* Mapper */
    protected ComponentMapper<T> specificComponentMapper;

    public void getSpecificComponentClass() {
        this.classType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public DestroyedByScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        /* Collide with: Boundary */
        T specificComponent = specificComponentMapper.get(contactEntity);
        if (specificComponent != null) {
            Gdx.app.getApplicationLogger().debug("DestroyedByScript (" + classType.getSimpleName() + ")", "Destroy EntityID: " + this.getEntity());
            this.getEngine().delete(this.getEntity());
        }
    }

    @Override
    public void dispose() {
        // do nothing
    }
}
