package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.sakurawald.Maltose;
import com.sakurawald.manager.ApplicationAssetManager;

@SuppressWarnings("FieldCanBeLocal")
public class AboutScreen extends Scene2DScreen {

    private Table table;
    private Label label_title;
    private TextArea textarea_content;
    private TextButton textbutton_back;

    protected void initializeStageActors() {

        table = new Table();
        table.top();
        table.setFillParent(true);
        Skin skin = this.getSkin();

        label_title = new Label("About", skin);
        label_title.setAlignment(Align.center);
        table.add(label_title).expandX().fill().pad(10).row();

        textarea_content = new TextArea(Gdx.files.internal("text/about_screen_content.txt").readString("UTF-8"), skin);
        textarea_content.setDisabled(true);
        table.add(textarea_content).expandX().expandY().fill().pad(10).row();

        textbutton_back = new TextButton("Back", skin);
        table.add(textbutton_back).expandX().fill().pad(10);

        this.getStage().addActor(table);
    }

    protected void registerStageEvents() {
        textbutton_back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maltose.getInstance().setScreen(new MainMenuScreen());
            }
        });
    }

}
