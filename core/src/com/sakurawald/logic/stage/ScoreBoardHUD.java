package com.sakurawald.logic.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.manager.ApplicationAssetManager;
import games.rednblack.editor.renderer.SceneLoader;
import lombok.Getter;

import java.util.Calendar;

// TODO fix max icon bug.
public class ScoreBoardHUD extends Stage {

    @Getter
    private final Viewport viewport;
    @Getter
    private final SceneLoader sceneLoader;
    @Getter
    private final PlayerComponent playerComponent;

    private final Label label_collected_tokens;
    private final Label label_play_time_seconds;
    private final Label label_left_lives;

    private int collected_tokens = -1;
    private int play_time_seconds = -1;
    private int left_lives = -1;

    public ScoreBoardHUD(PlayerComponent playerComponent, Viewport viewport, SceneLoader sceneLoader) {
        super(viewport, sceneLoader.getBatch());

        /* Init properties */
        this.playerComponent = playerComponent;
        this.viewport = viewport;
        this.sceneLoader = sceneLoader;

        /* Init Table UI */
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        label_left_lives = new Label(null, ApplicationAssetManager.getInstance().getSkin());
        table.add(label_left_lives).expandX().padTop(5);

        label_collected_tokens = new Label(null, ApplicationAssetManager.getInstance().getSkin());
        table.add(label_collected_tokens).expandX().padTop(10);

        label_play_time_seconds = new Label(null ,ApplicationAssetManager.getInstance().getSkin());
        table.add(label_play_time_seconds).expandX().padTop(10);

        this.addActor(table);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /* Render the actors of the stage */
        if (left_lives != this.getPlayerComponent().leftLives) {
            left_lives = this.getPlayerComponent().leftLives;
            label_left_lives.setText("Left Lives: " + left_lives);
        }

        if (collected_tokens != this.getPlayerComponent().tokenCollected) {
            collected_tokens = this.getPlayerComponent().tokenCollected;
            label_collected_tokens.setText("Collected Tokens: " + collected_tokens);
        }

        int current_play_time_seconds = (int) ((Calendar.getInstance().getTimeInMillis() - this.getPlayerComponent().startGameTimestamp) / 1000);
        if (play_time_seconds != current_play_time_seconds) {
            play_time_seconds = current_play_time_seconds;
            label_play_time_seconds.setText("Play Time: " + play_time_seconds + "s");
        }
    }
}
