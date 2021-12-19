package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import lombok.Getter;

import java.util.ArrayList;

@SuppressWarnings({"UnusedReturnValue", "rawtypes", "unchecked"})
public class ApplicationAssetManager {

    /* Static */
    private static final String SKIN_JSON_PATH = "skin/skin.json";

    @Getter
    private static final ApplicationAssetManager instance = new ApplicationAssetManager();

    /* Member Properties */
    @Getter
    private final AssetManager assetManager;
    @Getter
    private final AsyncResourceManager asyncResourceLoader;
    @Getter
    private final Skin skin;
    @Getter
    SceneConfiguration sceneConfiguration;

    public ApplicationAssetManager() {
        /* Create AssetManager */
        this.assetManager = new AssetManager();

        /* Load Assets */
        this.assetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(assetManager.getFileHandleResolver()));
        this.assetManager.load("project.dt", AsyncResourceManager.class);
        this.assetManager.load(SKIN_JSON_PATH, Skin.class);
        this.assetManager.finishLoading();

        /* Create ResourceManager */
        this.asyncResourceLoader = this.assetManager.get("project.dt", AsyncResourceManager.class);

        /* Load Skin */
        this.skin = this.assetManager.get(SKIN_JSON_PATH);
    }

    public static SceneLoader buildSceneLoader(SceneConfiguration sceneConfiguration) {
        return new SceneLoader(sceneConfiguration);
    }

    // Load Composite Item from HyperLap2D's Library and add component
    public static int createEntityFromLibrary(SceneLoader sceneLoader, String libraryName, String layer, float posX, float posY, ArrayList<Class<?>> createComponentClasses) {
        Gdx.app.getApplicationLogger().debug("ApplicationAssetManager", "Creating Entity from Library: " + libraryName + " Layer: " + layer);
        /* Load Composite from Library */
        CompositeItemVO tmpComposite = sceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;
        return createEntityFromCompositeVO(sceneLoader, tmpComposite, createComponentClasses);
    }

    public static int createEntityFromCompositeVO(SceneLoader sceneLoader, CompositeItemVO compositeItemVO, ArrayList<Class<?>> createComponentClasses) {
        /* Create the Entity */
        int entityID = sceneLoader.getEntityFactory().createEntity(sceneLoader.getRoot(), compositeItemVO);
        sceneLoader.getEntityFactory().initAllChildren(entityID, compositeItemVO.composite);

        /* Create Components (Unchecked Generics can't use forEach() method) */
        for (Class componentClass : createComponentClasses) {
            sceneLoader.getEngine().edit(entityID).create(componentClass);
        }
        return entityID;
    }
}
