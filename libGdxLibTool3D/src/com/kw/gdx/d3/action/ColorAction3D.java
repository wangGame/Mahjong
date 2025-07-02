package com.kw.gdx.d3.action;

import com.badlogic.gdx.graphics.Color;

public class ColorAction3D extends TemporalAction3D{
    private Color startColor;
    private Color endColor;
    protected void begin () {
        startColor = target.getColor();
    }

    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }

    protected void update (float percent) {
        startColor.lerp(endColor,percent);
        target.setColor(startColor);
    }

    public void reset () {
        super.reset();
    }
}
