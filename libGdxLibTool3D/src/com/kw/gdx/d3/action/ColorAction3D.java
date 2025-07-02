package com.kw.gdx.d3.action;

import com.badlogic.gdx.graphics.Color;

public class ColorAction3D extends TemporalAction3D{
    private float s;
    private float a;
    protected void begin () {
        s = target.getColor().a;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    protected void update (float percent) {
        float xxa = s - (s - a) * percent;
        target.setColorA(xxa);
    }

    public void reset () {
        super.reset();
    }
}
