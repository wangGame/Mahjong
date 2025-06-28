package com.kw.gdx.d3.bean;

import com.badlogic.gdx.math.Vector3;
import com.kw.gdx.d3.actor.BaseActor3D;

public class RayBean {
    private float length;
    private Vector3 vector3;
    private BaseActor3D target;

    public Vector3 getVector3() {
        return vector3;
    }

    public void setVector3(Vector3 vector3) {
        this.vector3 = vector3;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public BaseActor3D getBaseActor3D() {
        return target;
    }

    public void setBaseActor3D(BaseActor3D baseActor3D) {
        this.target = baseActor3D;
    }

    public void reset() {
        length = Float.MAX_VALUE;
        vector3 = new Vector3(0,0,0);
        target = null;
    }
}
