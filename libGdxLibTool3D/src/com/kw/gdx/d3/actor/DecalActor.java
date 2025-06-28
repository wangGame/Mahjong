package com.kw.gdx.d3.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.kw.gdx.asset.Asset;

public class DecalActor extends BaseActor3D{
    private Decal decal;

    public DecalActor(String path){
         this.decal = Decal.newDecal(new TextureRegion(Asset.getAsset().getTexture(path)),true);
    }

    private Vector3 positionV3 = new Vector3();
    private Quaternion rotationP = new Quaternion();
    private Vector3 scaleV3 = new Vector3();
    private Quaternion temp = new Quaternion();
    @Override
    protected void drawDecal(DecalBatch decalBatch) {
        super.drawDecal(decalBatch);
        BaseActor3DGroup parent3D1 = parent3D;
        Matrix4 actorMatrix1 = parent3D1.getActorMatrix();
        actorMatrix1.getTranslation(positionV3);
        actorMatrix1.getRotation(rotationP);
        actorMatrix1.getScale(scaleV3);
        temp.set(rotationP);
        temp.mul(rotation);
        decal.setRotation(temp);
        decal.setPosition(positionV3.add(getPosition()));
        decal.setScale(scaleV3.x*getScale().x,scaleV3.y*getScale().y);
        decalBatch.add(decal);
    }
}
