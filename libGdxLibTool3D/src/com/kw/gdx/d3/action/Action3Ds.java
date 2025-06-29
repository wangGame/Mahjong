package com.kw.gdx.d3.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class Action3Ds extends Actions {
    static public <T extends Action3D> T action3D(Class<T> type) {
        Pool<T> pool = Pools.get(type);
        T action = pool.obtain();
        action.setPool(pool);
        return action;
    }

    static public AddAction3D addAction3D(Action3D action3D) {
        AddAction3D addAction3D = Pools.obtain(AddAction3D.class);
        addAction3D.setAction(action3D);
        return addAction3D;
    }

    static public IntAction3D intAction3D(int start, int end, Interpolation interpolation, float duration) {
        IntAction3D intAction3D = Pools.obtain(IntAction3D.class);
        intAction3D.setStart(start);
        intAction3D.setEnd(end);
        intAction3D.setInterpolation(interpolation);
        intAction3D.setDuration(duration);
        return intAction3D;
    }

    static public MoveToAction3D moveToAction3D(float x, float y, float z, float time, Interpolation interpolation) {
        MoveToAction3D moveToAction3D = Pools.obtain(MoveToAction3D.class);
        moveToAction3D.setPosition(x, y, z);
        moveToAction3D.setDuration(time);
        moveToAction3D.setInterpolation(interpolation);
        return moveToAction3D;
    }

    static public RotateToAction3D rotation3D(float rotationX, float rotationY, float rotationZ, float time, Interpolation interpolation) {
        RotateToAction3D action3D = Pools.obtain(RotateToAction3D.class);
        action3D.setEndX(rotationX);
        action3D.setEndY(rotationY);
        action3D.setEndZ(rotationZ);
        action3D.setDuration(time);
        action3D.setInterpolation(interpolation);
        return action3D;
    }

    static public RepeatAction3D forever3D(Action3D repeatedAction) {
        RepeatAction3D action = action3D(RepeatAction3D.class);
        action.setCount(RepeatAction.FOREVER);
        action.setAction(repeatedAction);
        return action;
    }

    static public ParallelAction3D parallel3D(Action3D... action) {
        ParallelAction3D parallelAction3D = action3D(ParallelAction3D.class);
        for (Action3D action3D : action) {
            parallelAction3D.addAction(action3D);
        }
        return parallelAction3D;
    }

    static public SequenceAction3D sequenceAction3D(Action3D... action){
        SequenceAction3D sequenceAction3D = new SequenceAction3D();
        for (Action3D action3Ds : action) {
            sequenceAction3D.addAction(action3Ds);
        }
        return sequenceAction3D;
    }
}
