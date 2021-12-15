package com.sakurawald.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sakurawald.Maltose;

public class AboutScreen extends Scene2DScreen {

    private Label label_title;
    private TextArea textarea_content;
    private TextButton textbutton_back;

    public AboutScreen() {
        super("scene_about.json");
    }

    @Override
    protected void initializeStage() {
        label_title = getStage().getRoot().findActor("label_title");
        label_title.setText("About");
        textarea_content = getStage().getRoot().findActor("textarea_content");
        textarea_content.setText("Hi there, blah blah blah ");
        textbutton_back = getStage().getRoot().findActor("textbutton_back");
        textbutton_back.setText("Back");
    }

    @Override
    protected void registerStageEvents() {
        textbutton_back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maltose.getInstance().setScreen(new MainMenuScreen());
            }
        });
    }

}
