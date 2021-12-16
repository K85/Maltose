package com.sakurawald.manager;

import com.artemis.PooledComponent;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import lombok.Getter;

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
    public static <T extends PooledComponent> void loadCompositeFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY, Class<T> componentClass){
        System.out.println("HelperClass - loadCompositeFromLib");

        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(tmpEntity,tmpComposite.composite);
        mSceneLoader.getEngine().edit(tmpEntity).create(componentClass);
    }

}
