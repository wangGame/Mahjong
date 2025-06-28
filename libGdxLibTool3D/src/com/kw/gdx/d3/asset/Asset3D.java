package com.kw.gdx.d3.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.UBJsonReader;
import com.kw.gdx.asset.Asset;

public class Asset3D {
    private AssetManager assetManager;
    private static Asset3D asset3D;
    private Asset3D(){
        assetManager = Asset.getAsset().getAssetManager();
        assetManager.setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), assetManager.getFileHandleResolver()));
        assetManager.setLoader(Model.class, ".obj", new ObjLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(ParticleEffect.class,new ParticleEffectLoader(assetManager.getFileHandleResolver()));
    }

    public static Asset3D getAsset3D(){
        if (asset3D == null){
            asset3D = new Asset3D();
        }
        return asset3D;
    }

    public Model getModel(String path) {
        if (!assetManager.isLoaded(path)) {
            assetManager.load(path, Model.class);
            assetManager.finishLoading();
        }
        return assetManager.get(path,Model.class);
    }

    public ParticleEffect getParticle(String particle, Array<ParticleBatch<?>> particleBatch){
        if (!assetManager.isLoaded(particle)) {
            ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleBatch);
            assetManager.load(particle, ParticleEffect.class,loadParam);
            assetManager.finishLoading();
        }
        return assetManager.get(particle);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
