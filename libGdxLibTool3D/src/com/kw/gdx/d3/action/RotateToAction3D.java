package com.kw.gdx.d3.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;

public class RotateToAction3D extends TemporalAction3D {
    private float startX,startY,startZ;
    private float endX,endY,endZ;
    private boolean useShortestDirection = false;
    private Quaternion quaternionX;
    private Quaternion quaternionY;
    private Quaternion quaternionZ;


    public RotateToAction3D () {
        quaternionX = new Quaternion();
        quaternionY = new Quaternion();
        quaternionZ = new Quaternion();
    }

    /** @param useShortestDirection Set to true to move directly to the closest angle */
    public RotateToAction3D (boolean useShortestDirection) {
        this.useShortestDirection = useShortestDirection;
    }

    protected void begin () {
        startX = target.getAngle();
        startY = target.getAngle();
        startZ = target.getAngle();
    }

    protected void update (float percent) {
        float rotationX;
        float rotationY;
        float rotationZ;

        if (percent == 0) {
            rotationX = startX;
            rotationY = startY;
            rotationZ = startZ;
        }else if (percent == 1){
            rotationX = endX;
            rotationY = endY;
            rotationZ = endZ;
        }else if (useShortestDirection){
            rotationX = MathUtils.lerpAngleDeg(this.startX, this.endZ, percent);
            rotationY = MathUtils.lerpAngleDeg(this.startY, this.endZ, percent);
            rotationZ = MathUtils.lerpAngleDeg(this.startZ, this.endZ, percent);
        } else {
            rotationX = startX + (endX - startX) * percent;
            rotationY = startY + (endY - startY) * percent;
            rotationZ = startZ + (endZ - startZ) * percent;
        }

        // 将旋转角度转换为四元数
        quaternionX.setFromAxis(1,0,0,rotationX);
        quaternionY.setFromAxis(0,1,0,rotationY);
        quaternionZ.setFromAxis(0,0,1,rotationZ);

        Quaternion mul = quaternionX.mul(quaternionY).mul(quaternionZ);
        System.out.println(rotationX+"  "+rotationY+"  "+rotationZ);
        target.setRotation(mul);
    }

    public boolean isUseShortestDirection () {
        return useShortestDirection;
    }

    public void setUseShortestDirection (boolean useShortestDirection) {
        this.useShortestDirection = useShortestDirection;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public void setEndZ(float endZ) {
        this.endZ = endZ;
    }

}
