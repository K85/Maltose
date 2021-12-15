package com.sakurawald.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.data.component.DiamondComponent;
import com.sakurawald.data.component.PlayerComponent;
import com.sakurawald.data.system.CameraSystem;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

public class ApplicationAssetManager {

    /* Constants */
    @Getter
    private static final ApplicationAssetManager instance = new ApplicationAssetManager();

    /* Common Props */
    @Getter
    private AssetManager assetManager;
    @Getter
    private AsyncResourceManager asyncResourceLoader;
    @Getter
    private SceneLoader sceneLoader;
    @Getter
    private ItemWrapper rootItemWrapper;

    public ApplicationAssetManager() {

        /* Create AssetManager */
        assetManager = new AssetManager();

        // Load Assets
        assetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(assetManager.getFileHandleResolver()));
        assetManager.load("project.dt", AsyncResourceManager.class);
        assetManager.load("skin/skin.json", Skin.class);
        assetManager.finishLoading();

        /* Init the ItemWrapper of root */
        rootItemWrapper = new ItemWrapper(sceneLoader.getRoot());

        /* Create ResourceManager */
        asyncResourceLoader = assetManager.get("project.dt", AsyncResourceManager.class);
        sceneLoader = new SceneLoader(asyncResourceLoader);
    }

}
