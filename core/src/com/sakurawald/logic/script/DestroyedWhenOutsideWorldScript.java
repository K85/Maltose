package com.sakurawald.logic.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.screen.GameScreen;

public class DestroyedWhenOutsideWorldScript extends ApplicationScript{

    public DestroyedWhenOutsideWorldScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void doAct(float delta) {
        /* Destroy when outside world */
        if (this.getPhysicsBodyComponent() != null && this.getPhysicsBodyComponent().body != null) {
            Vector2 position = this.getPhysicsBodyComponent().body.getPosition();
            if (this.getGameScreen().isOutsideWorld(position)) {
                Gdx.app.getApplicationLogger().debug("DestroyedWhenOutsideWorldScript", "Outside of world auto destroy EntityID: " + this.getEntity());
                this.getEngine().delete(this.getEntity());
            }
        }
    }
}
