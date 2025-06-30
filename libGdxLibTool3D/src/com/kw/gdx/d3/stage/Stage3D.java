package com.kw.gdx.d3.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.kw.gdx.d3.actor.BaseActor3D;
import com.kw.gdx.d3.actor.BaseActor3DGroup;
import com.kw.gdx.d3.bean.RayBean;

/**
 * 建议复写
 */
public class Stage3D extends InputAdapter {
    protected Environment environment;
    protected PerspectiveCamera camera;
    protected BaseActor3DGroup gameRoot;
    protected ModelBatch modelBatch;
    protected float intervalCounter;
    protected final float INTERVAL_COUNTER_FREQUENCY = 1;
    protected CameraInputController camController;//视角控制器
    protected DirectionalShadowLight shadowLight;
    protected ModelBatch shadowBatch;
    protected RayBean rayBean = new RayBean();
    protected ShapeRenderer debugShapes;
    protected boolean isDebug;
    protected DecalBatch decalBatch;

    public Stage3D() {
        environment = new Environment();
        initLight();
        initCamera();
        initModelBatch();
        initDecalBatch();
        initRoot();
    }

    protected void initDecalBatch() {
        decalBatch = new DecalBatch(new CameraGroupStrategy(camera));
    }

    public void debug() {
        if (isDebug) {
            if (debugShapes == null) {
                debugShapes = new ShapeRenderer();
                debugShapes.setAutoShapeType(true);
            }
        }

        debugShapes.setProjectionMatrix(getCamera().combined);
        debugShapes.begin();
        gameRoot.drawDebug(debugShapes);
        debugShapes.end();
    }

    protected void initRoot() {
        gameRoot = new BaseActor3DGroup(0, 0, 0);
    }

    public CameraInputController getCamController() {
        return camController;
    }

    protected void initModelBatch() {
        DefaultShader.Config config = new DefaultShader.Config();
        config.numDirectionalLights = 1;
        config.numPointLights = 50;
        config.numSpotLights = 0;
        ShaderProvider shaderProvider = new DefaultShaderProvider(config);
        this.modelBatch = new ModelBatch(shaderProvider);
        this.shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    protected void initCamera() {
        camera = new PerspectiveCamera(20, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(0f, 120, 40f);
        camera.lookAt(0,0,0);

        camera.near = 0.3f;
        camera.far = 11300f;
        camController = new CameraInputController(camera);
    }

    protected void initLight() {
//        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 1f));//环境光
        //投影
        shadowLight = new DirectionalShadowLight(1024, 1024, 300f, 300f,
                1f, 100f);
//        environment.add();
//        environment.shadowMap = (ShadowMap) shadowLight;
        DirectionalLight set = new DirectionalLight().set(0.5f, 0.5f, 0.5f, 30, -30, 1);
        float intensity = 0.2f;
        Color color = Color.valueOf("#FFF4D6");
        color.r = color.r * intensity;
        color.g = color.g * intensity;
        color.b = color.b * intensity;
        color.a = 0.1f;
        set.setColor(color);
//        environment.add(set);
        PointLight set1 = new PointLight().set(0.5f, .5f, 0.5f, 10.0f, 20.0f, 0.0f, 220.3f);
        PointLight set2 = new PointLight().set(0.5f, .5f, 0.5f, -10.0f, 20.0f, 0.0f, 220.3f);
        PointLight set3 = new PointLight().set(0.5f, .5f, 0.5f, 10.0f, 20.0f, -10.0f, 220.3f);

        environment.add(set1);
        environment.add(set2);
        environment.add(set3);

    }

    public void act(float dt) {
        camera.update();
        gameRoot.act(dt);
        setIntervalFlag(dt);
    }

    public void draw() {
        shadowLight.begin(Vector3.Zero, camera.direction);
        shadowBatch.begin(shadowLight.getCamera());
        gameRoot.drawShadow(shadowBatch,environment);
        shadowBatch.end();
        shadowLight.end();
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        modelBatch.begin(camera);
        gameRoot.draw(modelBatch, environment);
        modelBatch.end();
//        debug();
        drawDecal();
    }

    public void drawDecal() {

        camera.update();
        gameRoot.drawDecal(decalBatch);
        decalBatch.flush();
    }

    public void dispose() {
        modelBatch.dispose();
    }

    public void addActor(BaseActor3D ba) {
        gameRoot.addActor3D(ba);
        ba.setStage3D(this);
    }

    public void removeActor(BaseActor3D ba) {
        gameRoot.remove3D(ba);
    }

    public BaseActor3DGroup getRoot() {
        return gameRoot;
    }

    public void setCameraPosition(float x, float y, float z) {
        camera.position.set(x, y, z);
    }

    public void setCameraPosition(Vector3 v) {
        camera.position.set(v);
    }

    public void moveCamera(float x, float y, float z) {
        camera.position.add(x, y, z);
    }

    public void moveCamera(Vector3 v) {
        camera.position.add(v);
    }

    public void moveCameraForward(float dist) {
        Vector3 forward = new Vector3(camera.direction.x, 0, camera.direction.z).nor();
        moveCamera(forward.scl(dist));
    }

    public void moveCameraRight(float dist) {
        Vector3 right = new Vector3(camera.direction.z, 0, -camera.direction.x).nor();
        moveCamera(right.scl(dist));
    }

    public void moveCameraUp(float dist) {
        moveCamera(dist, 0, 0);
    }

    public void setCameraDirection(Vector3 v) {
        camera.lookAt(v);
        camera.up.set(0, 1, 0);
    }

    public void setCameraDirection(float x, float y, float z) {
        setCameraDirection(new Vector3(x, y, z));
    }

    public void turnCameraX(float angle) {
        camera.rotate(Vector3.X, -angle);
    }

    public float getCameraRotation() {
        float camAngle = -(float) Math.atan2(camera.up.x, camera.up.y) * MathUtils.radiansToDegrees + 180;
        return camAngle;
    }

    public void rollCamera(float angle) {
        camera.up.set(Vector3.X);
        camera.rotate(camera.direction, angle);
    }

    public void tiltCamera(float angle) {
        Vector3 side = new Vector3(0, -camera.up.y, -camera.up.z);
        camera.direction.rotate(side, angle);
    }

    protected void setIntervalFlag(float dt) {
        if (intervalCounter > INTERVAL_COUNTER_FREQUENCY) {

            intervalCounter = 0;
        } else {

            intervalCounter += dt;
        }
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        rayBean.reset();
        Ray ray = camera.getPickRay(screenX, screenY);
        //这个是root
        getRoot().checkCollisions(ray, rayBean);
        BaseActor3D baseActor3D = rayBean.getBaseActor3D();
        if (rayBean.getBaseActor3D() != null) {
            baseActor3D.touchUp(rayBean.getVector3(), pointer, button);
            return true;
        }
        return false;
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }
}