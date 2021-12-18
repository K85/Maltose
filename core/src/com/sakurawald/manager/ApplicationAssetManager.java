package com.sakurawald.manager;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.widget.ListView;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"UnusedReturnValue", "rawtypes", "unchecked"})
public class ApplicationAssetManager {

    /* Constants */
    private static final String SKIN_JSON_PATH = "skin/skin.json";

    @Getter
    private static final ApplicationAssetManager instance = new ApplicationAssetManager();

    /* Common Props */
    @Getter
    private AssetManager assetManager;
    @Getter
    private AsyncResourceManager asyncResourceLoader;
    @Getter
    private Skin skin;
    @Getter
    SceneConfiguration sceneConfiguration;

    public ApplicationAssetManager() {

        /* Create AssetManager */
        this.assetManager = new AssetManager();

        // Load Assets
        this.assetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(assetManager.getFileHandleResolver()));
        this.assetManager.load("project.dt", AsyncResourceManager.class);
        this.assetManager.load(SKIN_JSON_PATH, Skin.class);
        this.assetManager.finishLoading();

        /* Create ResourceManager */
        this.asyncResourceLoader = this.assetManager.get("project.dt", AsyncResourceManager.class);

        /* Assign Skin */
        this.skin = this.assetManager.get(SKIN_JSON_PATH);
    }

    public SceneLoader makeSceneLoader(SceneConfiguration sceneConfiguration) {
        return new SceneLoader(sceneConfiguration);
    }

    // Load Composite Item from HyperLap2D's Library and add component
    public static int loadCompositeFromLibrary(SceneLoader sceneLoader, String libraryName, String layer, float posX, float posY, ArrayList<Class<?>> createComponentClasses) {
        Gdx.app.log("HelperClass - loadCompositeFromLib", "libraryName: " + libraryName + " layer: " + layer + " posX: " + posX + " posY: " + posY);

        CompositeItemVO tmpComposite = sceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int entityID = sceneLoader.getEntityFactory().createEntity(sceneLoader.getRoot(), tmpComposite);
        sceneLoader.getEntityFactory().initAllChildren(entityID, tmpComposite.composite);

        for (Class componentClass : createComponentClasses) {
            sceneLoader.getEngine().edit(entityID).create(componentClass);
        }

        return entityID;
    }

    public static int loadMagicCompositeFromLibrary(SceneLoader sceneLoader, CompositeItemVO compositeItemVO, ArrayList<Class<?>> createComponentClasses) {

        int entityID = sceneLoader.getEntityFactory().createEntity(sceneLoader.getRoot(), compositeItemVO);
        sceneLoader.getEntityFactory().initAllChildren(entityID, compositeItemVO.composite);

        for (Class componentClass : createComponentClasses) {
            sceneLoader.getEngine().edit(entityID).create(componentClass);
        }

        return entityID;
    }
}
