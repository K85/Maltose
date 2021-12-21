package com.sakurawald.logic.bean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sakurawald.logic.enums.PlayerInstruction;
import com.sakurawald.manager.PlayerManager;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

/* A player is an abstract of the real world player.*/
public class Player {

    /* Constants */
    public static final float PLAYER_MAX_VELOCITY = 1;

    @Getter
    private final PlayerManager owner;

    @Getter
    private final ItemWrapper playerItemWrapper;

    public Player(PlayerManager owner, ItemWrapper playerItemWrapper) {
        this.owner = owner;
        this.playerItemWrapper = playerItemWrapper;
    }

    public void sendInstruction(PlayerInstruction playerInstruction) {
        Gdx.app.getApplicationLogger().debug("sendInstruction", "player = " + this + ", playerInstruction: " + playerInstruction);

        /* Handle the instruction */
        Body body = this.getPlayerItemWrapper().getComponent(PhysicsBodyComponent.class).body;
        switch (playerInstruction) {
            case MOVE_LEFT:
                if (body.getLinearVelocity().x > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(-PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case MOVE_RIGHT:
                if (body.getLinearVelocity().x < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case MOVE_UP:
                if (body.getLinearVelocity().y < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
            case MOVE_DOWN:
                if (body.getLinearVelocity().y > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, -PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
            case SHOOT:
                this.getOwner().getGameScreen().getBulletManager().createBullet();
                break;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "owner=" + owner +
                ", entityID=" + playerItemWrapper.getEntity() +
                '}';
    }
}
