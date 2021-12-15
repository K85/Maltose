package com.sakurawald.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sakurawald.Maltose;

public class MainMenuScreen extends Scene2DScreen {

    private TextButton textbutton_start;
    private TextButton textbutton_about;
    private TextButton textbutton_exit;

    public MainMenuScreen() {
        super("scene_main.json");
    }

    @Override
    protected void initializeStage() {
        textbutton_start =  getStage().getRoot().findActor("textbutton_start");
        textbutton_start.setText("Start");
        textbutton_about = getStage().getRoot().findActor("textbutton_about");
        textbutton_about.setText("About");
        textbutton_exit = getStage().getRoot().findActor("textbutton_exit");
        textbutton_exit.setText("Exit");
    }

    @Override
    protected void registerStageEvents() {
        textbutton_start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("start clieked event !");
                Maltose.getInstance().setScreen(new GameScreen());
            }
        });

        textbutton_about.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("about clieked event !");
                Maltose.getInstance().setScreen(new AboutScreen());
            }
        });

        textbutton_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
    }

}
