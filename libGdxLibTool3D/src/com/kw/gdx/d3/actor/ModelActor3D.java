package com.kw.gdx.d3.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.d3.bean.RayBean;
import com.kw.gdx.d3.stage.Stage3D;

import javax.swing.text.AttributeSet;

public class ModelActor3D extends BaseActor3D {
    private Matrix4 resourceMax = new Matrix4();
    protected ModelInstance modelInstance;
    protected Array<Decal> decals;
    public ModelActor3D() {
        this(0, 0, 0);
    }

    public ModelActor3D(Model tableModel){
        this(0,0,0,tableModel);
    }

    public ModelActor3D(float x, float y, float z) {
        this(x,y,z,null);
    }

    public ModelActor3D(float x, float y, float z, Model model){
        if (model!=null){
            ModelInstance instance = new ModelInstance(model,getPosition());
            setModelInstance(instance);
        }
        setPosition(x,y,z);
        this.decals = new Array<>();
    }



    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        Material material = modelInstance.materials.get(0);
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        modelInstance.calculateBoundingBox(bounds);
    }

    public void drawShadow(ModelBatch batch, Environment environment) {
        if (modelInstance != null) {
//            if (!isCaremaClip()) return;
            Matrix4 matrix4 = calculateTransform();
            if (parent3D != null) {
                Matrix4 pM = parent3D.getActorMatrix();
                resourceMax.idt();
                resourceMax.set(pM);
                resourceMax.mul(matrix4);
                modelInstance.transform.set(resourceMax);
            } else {
                modelInstance.transform.set(matrix4);
            }
            batch.render(modelInstance);
        }
    }

    public void draw(ModelBatch batch, Environment env) {
        if (!isVisible)return;
        if (modelInstance != null) {
//            if (!isCaremaClip()) return;
            Matrix4 matrix4 = calculateTransform();
            if (parent3D != null) {
                Matrix4 pM = parent3D.getActorMatrix();
                resourceMax.idt();
                resourceMax.set(pM);
                resourceMax.mul(matrix4);
                modelInstance.transform.set(resourceMax);
            } else {
                modelInstance.transform.set(matrix4);
            }
            batch.render(modelInstance, env);
        }
    }


    public void setColor(Color c) {
        Material material = modelInstance.materials.get(0);
        ColorAttribute diffuse = (ColorAttribute)material.get(ColorAttribute.Diffuse);
        diffuse.color.set(c.r,c.g,c.b,c.a);
//            m.set(ColorAttribute.createDiffuse(c));
    }

    public void setColorA(float a) {
        Material material = modelInstance.materials.get(0);
        ColorAttribute diffuse = (ColorAttribute)material.get(ColorAttribute.Diffuse);
        diffuse.color.a = a;
    }

    public void buildModel(float width, float height, float depth, boolean blending) {
        ModelBuilder modelBuilder = new ModelBuilder();
        Material boxMaterial = new Material();
        if (blending)
            boxMaterial.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        int usageCode =
                VertexAttributes.Usage.Position +
                        VertexAttributes.Usage.ColorPacked +
                        VertexAttributes.Usage.Normal +
                        VertexAttributes.Usage.TextureCoordinates;
        Model boxModel = modelBuilder.createBox(width, height, depth, boxMaterial, usageCode);
        Vector3 position = new Vector3(0, 0, 0);
        ModelInstance modelInstance = new ModelInstance(boxModel, position);
        setModelInstance(modelInstance);
    }

    public void setStage3D(Stage3D stage3D) {
        this.stage3D = stage3D;
    }

    public void setMaterialTexture(Texture texture) {

        for (Material material : modelInstance.materials) {
            boolean setTexture = false;
            for (Attribute attribute : material) {
                if (attribute instanceof TextureAttribute) {
                    setTexture = true;
                    ((TextureAttribute) (attribute)).set(new TextureRegion(texture));
                }
            }
            if (!setTexture) {
                material.set(TextureAttribute.createDiffuse(new TextureRegion(texture)));
            }
        }
    }

    public void setParent3D(BaseActor3DGroup parent3D) {
        this.parent3D = parent3D;
    }

    public BaseActor3DGroup getParent3D() {
        return parent3D;
    }

    /**
     * 是否点击上
     *
     * @param ray
     * @param rayBean
     * @return
     */
    protected void checkCollision(Ray ray, RayBean rayBean) {
        Vector3 position1 = getPosition();
//        ray在使用之前已经经过变换
        boundingBoxTemp.set(position1.cpy().add(bounds.min), position1.cpy().add(bounds.max));
        checkCollisionV3.set(0, 0, 0);
        if (Intersector.intersectRayBounds(ray, boundingBoxTemp, checkCollisionV3)) {
            float dst = checkCollisionV3.dst(stage3D.getCamera().position);
            if (rayBean.getBaseActor3D() != null) {
                if (dst < rayBean.getLength()) {
                    rayBean.setBaseActor3D(this);
                    rayBean.setVector3(checkCollisionV3);
                    rayBean.setLength(dst);
                }
            } else {
                rayBean.setBaseActor3D(this);
                rayBean.setVector3(checkCollisionV3);
                rayBean.setLength(dst);
            }
        }
    }

    Vector3 dimensions = new Vector3();
    public void updateBox() {
        if (modelInstance != null) {
            Matrix4 matrix4 = calculateTransform();
            bounds.mul(matrix4);
            bounds.getDimensions(dimensions);
            radius = dimensions.len() / 2f;
            bounds.getCenter(center);
            bounds.mul(actorMatrix);
        }

    }

    //是否在裁剪坐标里面
    public boolean isCaremaClip() {
        if (stage3D != null) {
            modelInstance.transform.getTranslation(clipTempV3);
            clipTempV3.add(center);
            return stage3D.getCamera().frustum.sphereInFrustum(position, radius);
        }
        return false;
    }

    public ModelInstance getModel() {
        return modelInstance;
    }

    public void setMetal(){
        Attributes attributes = new Attributes();
        attributes.set(
                    ColorAttribute.createDiffuse(0.7f, 0.7f, 0.7f, 1.0f),
                    ColorAttribute.createSpecular(1.0f, 1.0f, 1.0f, 1.0f),
                    FloatAttribute.createShininess(100.0f));
        setMetal(attributes);
    }

    public void setMetal(Attributes attributes) {
        for (Material material : modelInstance.materials) {
            material.set(
                    attributes
            );
        }
    }

    public Array<Decal> getDecals() {
        return decals;
    }

    public void addDecal(Decal decal){
        decals.add(decal);
    }

    @Override
    protected void drawDecal(DecalBatch decalBatch) {
        super.drawDecal(decalBatch);
        for (Decal decal : decals) {
            decalBatch.add(decal);
        }
    }
    private final Vector3 viewDir = new Vector3();
    private final Vector3 decalPosition = new Vector3();

    @Override
    public void act(float delta) {
        super.act(delta);
//        for (Decal decal : decals) {
//            decal.setPosition(position);
//            viewDir.set(cam.direction).scl(-1);
//            go.healthBarDecal.setRotation(viewDir, Vector3.Y);
//            decalBatch.add(go.healthBarDecal);
//        }
    }
}
