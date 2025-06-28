package com.kw.gdx.d3.action;

public class MoveToAction3D extends TemporalAction3D{

    private float startX, startY,startZ;
    private float endX, endY,endZ;

    protected void begin () {
        startX = target.getX();
        startY = target.getY();
        startZ = target.getZ();
    }

    protected void update (float percent) {
        float x, y,z;
        if (percent == 0) {
            x = startX;
            y = startY;
            z = startZ;
        } else if (percent == 1) {
            x = endX;
            y = endY;
            z = endZ;
        } else {
            x = startX + (endX - startX) * percent;
            y = startY + (endY - startY) * percent;
            z = startZ + (endZ - startZ) * percent;
        }
        target.setPosition(x, y,z);
    }

    public void reset () {
        super.reset();
    }

    public void setStartPosition (float x, float y,float z) {
        startX = x;
        startY = y;
        startZ = z;
    }

    public void setPosition (float x, float y,float z) {
        endX = x;
        endY = y;
        endZ = z;
    }


    public float getX () {
        return endX;
    }

    public void setX (float x) {
        endX = x;
    }

    public float getY () {
        return endY;
    }

    public void setY (float y) {
        endY = y;
    }

    public void setZ(float endZ) {
        this.endZ = endZ;
    }

    /** Gets the starting X value, set in {@link #begin()}. */
    public float getStartX () {
        return startX;
    }

    /** Gets the starting Y value, set in {@link #begin()}. */
    public float getStartY () {
        return startY;
    }
}
