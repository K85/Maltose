package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import lombok.Getter;

import java.util.ArrayList;

@SuppressWarnings({"UnusedReturnValue", "rawtypes", "unchecked", "GDXJavaStaticResource"})
public class ApplicationAssetManager {

    /* Static */
    public static final String SKIN_JSON_PATH = "skin/skin.json";
    public static final String PARTICLE_JSON_PATH = "particle/";
    @Getter
    public static final AssetManager assetManager;
    @Getter
    public static final AsyncResourceManager asyncResourceLoader;
    @Getter
    public static final TextureAtlas textureAtlas;
    @Getter
    public static final Skin skin;

    static {
        /* Create AssetManager */
        assetManager = new AssetManager();

        /* Load Assets */
        assetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(assetManager.getFileHandleResolver()));
        assetManager.load("project.dt", AsyncResourceManager.class);
        assetManager.load(SKIN_JSON_PATH, Skin.class);
        assetManager.finishLoading();

        /* Create ResourceManager */
        asyncResourceLoader = assetManager.get("project.dt", AsyncResourceManager.class);

        /* Load TextureAtlas */
        textureAtlas = new TextureAtlas(Gdx.files.internal("orig/pack.atlas"));

        /* Load Skin */
        skin = assetManager.get(SKIN_JSON_PATH);
    }

    public static SceneLoader buildSceneLoader(SceneConfiguration sceneConfiguration) {
        return new SceneLoader(sceneConfiguration);
    }

    // Load Composite Item from HyperLap2D's Library and add component
    public static int createEntityFromLibrary(SceneLoader sceneLoader, String libraryName, String layer, float posX, float posY, ArrayList<Class<?>> initComponents) {
        Gdx.app.getApplicationLogger().debug("ApplicationAssetManager", "Creating Entity from Library: " + libraryName + " Layer: " + layer);
        /* Load Composite from Library */
        CompositeItemVO compositeItemVO = sceneLoader.loadVoFromLibrary(libraryName);
        compositeItemVO.layerName = layer;
        compositeItemVO.x = posX;
        compositeItemVO.y = posY;
        return createEntityFromCompositeVO(sceneLoader, compositeItemVO, initComponents);
    }

    public static int createEntityFromCompositeVO(SceneLoader sceneLoader, CompositeItemVO compositeItemVO, ArrayList<Class<?>> initComponents) {
        /* Create the Entity */
        int entityID = sceneLoader.getEntityFactory().createEntity(sceneLoader.getRoot(), compositeItemVO);
        sceneLoader.getEntityFactory().initAllChildren(entityID, compositeItemVO.composite);

        /* Create Components (Unchecked Generics can't use forEach() method) */
        // if we don't manually add the components for the entity, the entity will not have the components
        for (Class componentClass : initComponents) {
            sceneLoader.getEngine().edit(entityID).create(componentClass);
        }
        return entityID;
    }
}
