package com.sakurawald.logic.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.Maltose;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.screen.MainMenuScreen;
import lombok.Getter;

import java.util.Calendar;

@SuppressWarnings("FieldCanBeLocal")
public class ScoreBoardHUD extends Stage {

    @Getter
    private final GameScreen gameScreen;

    @Getter
    private final Viewport viewport;

    private final TextButton textbutton_back;
    private final Label label_collected_tokens;
    private final Label label_play_time_seconds;
    private final Label label_left_lives;

    private int collected_tokens = -1;
    private int play_time_seconds = -1;
    private int left_lives = -1;

    public ScoreBoardHUD(GameScreen gameScreen, Viewport viewport) {
        super(viewport, gameScreen.getSceneLoader().getBatch());

        /* Init properties */
        this.gameScreen = gameScreen;
        this.viewport = viewport;

        /* Init Table UI */
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Skin skin = ApplicationAssetManager.getSkin();
        textbutton_back = new TextButton("Back", skin);
        textbutton_back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maltose.getInstance().setScreen(new MainMenuScreen());
            }
        });
        table.add(textbutton_back).padLeft(10).padTop(10);

        label_left_lives = new Label(null, skin);
        table.add(label_left_lives).expandX().padTop(10);

        label_collected_tokens = new Label(null, skin);
        table.add(label_collected_tokens).expandX().padTop(10);

        label_play_time_seconds = new Label(null, skin);
        table.add(label_play_time_seconds).expandX().padTop(10);

        this.addActor(table);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /* Quit ? */
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && Gdx.input.isKeyPressed(Input.Keys.NUM_1) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))) {
            Maltose.getInstance().setScreen(new MainMenuScreen());
        }


        /* Check Component */
        PlayerComponent playerComponent = gameScreen.getPlayerManager().getSolePlayer().getPlayerItemWrapper().getComponent(PlayerComponent.class);

        if (playerComponent == null)
            return;

        /* Render the actors of the stage */
        if (left_lives != playerComponent.leftLives) {
            left_lives = playerComponent.leftLives;
            label_left_lives.setText("Left Lives: " + left_lives);
        }

        if (collected_tokens != playerComponent.tokenCollected) {
            collected_tokens = playerComponent.tokenCollected;
            label_collected_tokens.setText("Collected Tokens: " + collected_tokens);
        }

        int current_play_time_seconds = (int) ((Calendar.getInstance().getTimeInMillis() - playerComponent.startGameTimestamp) / 1000);
        if (play_time_seconds != current_play_time_seconds) {
            play_time_seconds = current_play_time_seconds;
            label_play_time_seconds.setText("Play Time: " + play_time_seconds + "s");
        }
    }

}
