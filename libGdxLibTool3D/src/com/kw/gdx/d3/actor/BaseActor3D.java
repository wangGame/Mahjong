package com.kw.gdx.d3.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.d3.bean.RayBean;
import com.kw.gdx.d3.action.Action3D;
import com.kw.gdx.d3.stage.Stage3D;
import com.kw.gdx.d3.world.WorldSystem;

/**
 * model Actor
 */
public class BaseActor3D {
    protected BaseActor3DGroup parent3D;
    protected Vector3 position;
    protected float radius;
    protected Stage3D stage3D;
    protected BoundingBox bounds;
    protected Quaternion rotation;
    protected Vector3 scale;
    private Array<Action3D> actions;
    protected boolean isDity;
    protected Vector3 center;
    protected Matrix4 actorMatrix;
    protected Vector3 clipTempV3;
    protected BoundingBox boundingBoxTemp;
    protected Vector3 checkCollisionV3;
    protected Color color;
    protected boolean debug;
    //显示   所有的都加上碰撞检测？？
    protected btRigidBody body;
    protected boolean isVisible;

    public BaseActor3D(){
        this(0,0,0);
    }

    public BaseActor3D(float x, float y, float z) {
        this.position = new Vector3(x, y, z);
        this.rotation = new Quaternion();
        this.scale = new Vector3(1, 1, 1);
        this.actorMatrix = new Matrix4();
        this.bounds = new BoundingBox();
        this.actions = new Array(0);
        this.clipTempV3 = new Vector3();
        this.center = new Vector3();
        this.boundingBoxTemp = new BoundingBox();
        this.checkCollisionV3 = new Vector3();
        this.isDity = true;
        this.isVisible = true;
        this.color = new Color(1,1,1,1);
    }

    public Matrix4 getActorMatrix() {
        calculateTransform();
        return actorMatrix;
    }

    /**
     * 计算当前的mat
     * @return
     */
    public Matrix4 calculateTransform() {
//        if (!isDity)return actorMatrix;
        actorMatrix.idt();
        Matrix4 rotate = actorMatrix.rotate(rotation);
        Matrix4 matrix4 = rotate
                .trn(
                    position.x,
                    position.y,
                    position.z)
                .scale(
                        scale.x,
                        scale.y,
                        scale.z);
        actorMatrix=  matrix4;
        return matrix4;
    }

    //更新位置
    public void act(float delta) {
        if (body!=null) {
            Vector3 centerOfMassPosition = body.getCenterOfMassPosition();
            setPosition(centerOfMassPosition.add(bodyOff));
        }
        Array<Action3D> actions = this.actions;
        if (actions.size == 0) return;
        if (stage3D != null) Gdx.graphics.requestRendering();
        try {
            for (int i = 0; i < actions.size; i++) {
                Action3D action = actions.get(i);
                if (action.act(delta) && i < actions.size) {
                    Action3D current = actions.get(i);
                    int actionIndex = current == action ? i : actions.indexOf(action, true);
                    if (actionIndex != -1) {
                        actions.removeIndex(actionIndex);
                        action.setActor3D(null);
                        i--;
                    }
                }
            }
        } catch (RuntimeException ex) {
            String context = toString();
            throw new RuntimeException("Actor: " + context.substring(0, Math.min(context.length(), 128)), ex);
        }
    }

    public void drawShadow(ModelBatch batch, Environment environment){

    }

    public void draw(ModelBatch batch, Environment env) {

    }

    public void setColor(Color c) {
        this.color.set(c.r,c.g,c.b,c.a);
    }

    public Color getColor() {
        return color;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 v) {
        position.set(v);
        isDity = true;
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        isDity = true;
    }

    public void moveBy(Vector3 v) {
        position.add(v);
        isDity = true;
    }

    public void moveBy(float x, float y, float z) {
        moveBy(new Vector3(x, y, z));
        isDity = true;
    }

    public void moveByForward(float dist) {
        moveBy(rotation.transform(new Vector3(0, 0, 1)).scl(dist));
        isDity = true;
    }

    public void moveByUp(float dist) {
        moveBy(rotation.transform(new Vector3(1, 0, 0)).scl(dist));
        isDity = true;
    }

    public void moveByRight(float dist) {
        moveBy(rotation.transform(new Vector3(0, 1, 0)).scl(dist));
        isDity = true;
    }

    public void moveForward(float dist){
        position.z += dist;
        isDity = true;
    }

    public void moveUp(float dist){
        position.y += dist;
        isDity = true;
    }

    public void moveRight(float dist){
        position.x -= dist;
        isDity = true;
    }

    public float getTurnAngle() {
        return rotation.getAngleAround(1, 0, 0);
    }

    public void setTurnAngle(float degrees) {
        rotation.set(new Quaternion(Vector3.X, degrees));
        isDity = true;
    }

    public void turnBy(float degrees) {
        rotation.mul(new Quaternion(Vector3.X, -degrees));
        isDity = true;
    }

    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
        isDity = true;
    }

    public void remove() {
        if (parent3D!=null) {
            parent3D.remove3D(this);
        }
        if (stage3D!=null) {
            setStage3D(null);
        }
    }

    public void setStage3D(Stage3D stage3D) {
        this.stage3D = stage3D;
    }

    public void addAction (Action3D action) {
        action.setActor3D(this);
        actions.add(action);
        if (stage3D != null) {
            Gdx.graphics.requestRendering();
        }
    }

    public void removeAction (Action3D action) {
        if (action != null && actions.removeValue(action, true)) action.setActor3D(null);
    }

    public Array<Action3D> getActions () {
        return actions;
    }

    public void clearActions () {
        for (int i = actions.size - 1; i >= 0; i--)
            actions.get(i).setActor3D(null);
        actions.clear();
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public float getAngle() {
        return rotation.getAngle();
    }

    public void setParent3D(BaseActor3DGroup parent3D) {
        this.parent3D = parent3D;
    }

    public BaseActor3DGroup getParent3D() {
        return parent3D;
    }

    /**
     * 是否点击上
     * @param ray
     * @param rayBean
     * @return
     */
    protected void checkCollision(Ray ray, RayBean rayBean) {
    }

    public void touchUp(Vector3 vector3, int pointer, int button){

    }

    public float getX(){
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getZ() {
        return position.z;
    }

    //设置角度
    public void setFromAxis(int x, int y, int z, float rotationA) {
        rotation.setFromAxis(x,y,z,rotationA);
        isDity = true;
    }

    public void setRotation(Quaternion quaternion){
        this.rotation.set(quaternion);
        isDity = true;
    }

    public void updateBox(){

    }

    public BoundingBox getBounds() {
        if (isDity){
            updateBox();
        }
        return bounds;
    }

    //是否在裁剪坐标里面
    public boolean isCaremaClip(){
        return false;
    }

    //碰撞一般为正方形或者圆之类的
    public void addCollision(){
//        WorldSystem.getInstance().createBody();
    }

    public void setBody(btRigidBody body) {
        this.body = body;
    }

    private Vector3 bodyOff = new Vector3();
    public void setBodyOff(Vector3 vector3) {
        this.bodyOff.set(vector3);
    }

    public void setEulerAngles(float ya,float y,float z){
        rotation.setEulerAngles(ya,y,z);
    }

    protected void drawDecal(DecalBatch decalBatch) {

    }


    public void addBody(float collisionScale,float mass){
        WorldSystem.getInstance().addCollision(getScale().cpy().scl(collisionScale),
                getPosition().cpy(),mass,null);
    }

    public void addBody(float collisionScale,float mass,BaseActor3D actor3D){
        WorldSystem.getInstance().addCollision(getScale().cpy().scl(collisionScale),
                getPosition().cpy(),mass,actor3D);
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
