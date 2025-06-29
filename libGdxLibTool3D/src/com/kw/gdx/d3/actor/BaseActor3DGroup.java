package com.kw.gdx.d3.actor;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.d3.bean.RayBean;
import com.kw.gdx.d3.stage.Stage3D;

public class BaseActor3DGroup extends BaseActor3D{
    private Array<BaseActor3D> actor3DS;
    private Array<Decal> decals;
    public boolean transform = true;

    public BaseActor3DGroup(){
        this.actor3DS = new Array<>();
        this.decals = new Array<>();
    }

    public BaseActor3DGroup(float x, float y, float z) {
        super(x, y, z);
        this.actor3DS = new Array<>();
        this.decals = new Array<>();
    }

    public void drawShadow(ModelBatch batch,Environment environment){
        super.drawShadow(batch,environment);
        for (BaseActor3D actor3D : actor3DS) {
            actor3D.drawShadow(batch,environment);
        }
    }

    @Override
    public void draw(ModelBatch batch, Environment env) {
        super.draw(batch,env);
        for (BaseActor3D actor3D : actor3DS) {
            actor3D.draw(batch,env);
        }
    }


    public Matrix4 getActorMatrix() {

        actorMatrix.idt();
        Matrix4 rotate = actorMatrix.rotate(rotation);
        Matrix4 matrix4 = rotate
                .scale(
                        scale.x,
                        scale.y,
                        scale.z)
                .trn(
                        position.x,
                        position.y,
                        position.z)
               ;
        actorMatrix=  matrix4;


        if (parent3D!=null){
            Matrix4 cpy = parent3D.getActorMatrix().cpy();
            cpy.mul(actorMatrix);
            actorMatrix.set(cpy);
        }
        return actorMatrix;
    }


    public void addActor3D(BaseActor3D ba) {
        ba.remove();
        actor3DS.add(ba);
        ba.setParent3D(this);
        ba.setStage3D(stage3D);
    }

    @Override
    public void setStage3D(Stage3D stage3D) {
        super.setStage3D(stage3D);
        for (BaseActor3D actor3D : actor3DS) {
            actor3D.setStage3D(stage3D);
        }
    }

    public void remove3D(BaseActor3D ba) {
        actor3DS.removeValue(ba,false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (BaseActor3D actor3D : actor3DS) {
            actor3D.act(delta);
        }
    }

    public Array<BaseActor3D> getActor3DS() {
        return actor3DS;
    }

    public BaseActor3D checkCollisions(Ray ray,RayBean rayBean) {
        Matrix4 transform = getActorMatrix();
        Matrix4 inv = transform.cpy().inv();
        Ray localRay = new Ray(ray.origin.cpy().mul(inv), ray.direction.cpy().mul(inv));
        super.checkCollision(localRay,rayBean);
        for (BaseActor3D actor : actor3DS) {
            if (actor instanceof BaseActor3DGroup){
                ((BaseActor3DGroup)(actor)).checkCollisions(localRay,rayBean);
            }else {
                actor.checkCollision(localRay, rayBean);
            }
        }
        return null;
    }

    public void drawDebug(ShapeRenderer debugShapes) {
        drawDebugBounds(debugShapes);
        for (BaseActor3D actor3D : actor3DS) {
//            actor3D.drawDebug(batch,env);
        }
    }

    protected void drawDebugBounds(ShapeRenderer shapes) {
        if (!debug) return;
//        shapes.set(ShapeRenderer.ShapeType.Line);
//        shapes.setColor(stage.getDebugColor());
//        shapes.rect(x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    public void drawDecal(DecalBatch decalBatch) {
        super.drawDecal(decalBatch);
        for (BaseActor3D actor3D : actor3DS) {
            actor3D.drawDecal(decalBatch);
        }
    }


}
