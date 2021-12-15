package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;
import lombok.experimental.UtilityClass;

@SuppressWarnings("GDXJavaStaticResource")
@UtilityClass
public class SkinAtlasManager {

//    public static final AssetDescriptor<Skin> MALTOSE_ATLAS = new AssetDescriptor<Skin>("skin.json",Skin .class,new SkinLoader.SkinParameter("pack/skin/Maltose.atlas"));

    public static final Skin skin = new Skin(Gdx.files.internal("skin/Maltose.json"));
    public static final SceneComposerStageBuilder stageBuilder = new SceneComposerStageBuilder();
    public static final String SCENE_PATH_PREFIX = "skin/scenes/";

    public static Stage buildStage(String jsonHandlePath) {
        Stage stage = new Stage(new ScreenViewport());
        stageBuilder.build(stage, skin, Gdx.files.internal(SCENE_PATH_PREFIX + jsonHandlePath));
        return stage;
    }


}
