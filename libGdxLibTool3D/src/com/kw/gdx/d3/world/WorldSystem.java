package com.kw.gdx.d3.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.d3.actor.BaseActor3D;
import com.kw.gdx.d3.constant.D3Contant;

public class WorldSystem {
    private static WorldSystem worldSystem;
    //body map 方便进行查找Actor,通过刚体，最好的做法是讲Actor存放在刚体中
    private ArrayMap<btRigidBody, BaseActor3D> rigidBodyBaseActor3DArrayMap;
    private btBroadphaseInterface broadphase;
    private btDefaultCollisionConfiguration collisionConfiguration;
    private btDispatcher dispatcher;
    private btSequentialImpulseConstraintSolver solver;
    private btDiscreteDynamicsWorld world;
    private DebugDrawer deBugDrawer;
    private PerspectiveCamera perspectiveCamera;

    private WorldSystem() {
        Bullet.init();
        this.rigidBodyBaseActor3DArrayMap = new ArrayMap<>();
        this.deBugDrawer = new DebugDrawer();
        this.broadphase = new btDbvtBroadphase(); //AABB
        this.collisionConfiguration = new btDefaultCollisionConfiguration(); //更加细粒度
        this.dispatcher = new btCollisionDispatcher(collisionConfiguration);
        this.solver = new btSequentialImpulseConstraintSolver();
        this.world = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        this.world.setGravity(D3Contant.worldGravity);
        this.world.setDebugDrawer(deBugDrawer);
    }

    public static WorldSystem getInstance() {
        if (worldSystem == null) {
            worldSystem = new WorldSystem();
        }
        return worldSystem;
    }

    public BaseActor3D rayTest(Vector3 rayStart, Vector3 rayEnd) {
        //检测碰撞到了什么
        ClosestRayResultCallback rayCallback = new ClosestRayResultCallback(rayStart, rayEnd);
        world.rayTest(rayStart, rayEnd, rayCallback);
        if (rayCallback.hasHit()) {
            btCollisionObject collisionObject = rayCallback.getCollisionObject();
            btRigidBody hitBody = (btRigidBody) collisionObject;
            BaseActor3D actor3D = rigidBodyBaseActor3DArrayMap.get(hitBody);
            if (actor3D != null) {
                return actor3D;
            }
        }
        return null;
    }

    public void update() {
        world.stepSimulation(1f / 60f, 10);
    }

    public void setCam1(PerspectiveCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }

    public void checkTouch(int screenX, int screenY) {
        Ray pickRay = perspectiveCamera.getPickRay(screenX, screenY);
        Vector3 origin = pickRay.origin;
        Vector3 end = pickRay.origin.cpy().add(pickRay.direction.scl(1000f));
        rayTest(origin, end);
    }

    public void addCollision(Vector3 size, Vector3 position, float mass, BaseActor3D actor3D) {
        // 创建静态刚体的碰撞形状
        btCollisionShape shape = new btBoxShape(size);  // 创建一个较大的立方体（地面）

        // 设置静态刚体的质量为 0
        // 静态刚体的质量为 0
        Vector3 inertia = new Vector3(0f, 0f, 0f);  // 静态刚体的惯性张量为零
        // 创建静态刚体的构造信息
        btRigidBody.btRigidBodyConstructionInfo rbInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, shape, inertia);
        // 设置静态刚体的新位置
        btTransform transform = new btTransform();
        transform.setIdentity();  // 重置为单位矩阵
        // 设置新的位置 (X, Y, Z)
        transform.setOrigin(position);  // 例如，将静态刚体位置设置为 (0, -5, 0)
        rbInfo.setStartWorldTransform(transform);
        // 创建静态刚体（实际上就是创建一个不可移动的物体）
        btRigidBody staticRigidBody = new btRigidBody(rbInfo);
        staticRigidBody.setFriction(1f);
        staticRigidBody.setDamping(0.9999999f, 100);
        // 将静态刚体添加到物理世界中
        world.addRigidBody(staticRigidBody);
        if (actor3D != null) {
            actor3D.setBody(staticRigidBody);
        }
        staticRigidBody.setSleepingThresholds(0.0f, 0.0f);
        if (actor3D != null) {
            rigidBodyBaseActor3DArrayMap.put(staticRigidBody, actor3D);
        }
    }
}
