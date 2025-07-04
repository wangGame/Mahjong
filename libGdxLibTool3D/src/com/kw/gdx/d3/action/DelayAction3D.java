package com.kw.gdx.d3.action;

import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;

/**
 * Author by tony
 * Date on 2025/6/30.
 */
public class DelayAction3D extends DelegateAction3D {
    private float duration, time;

    public DelayAction3D () {
    }

    public DelayAction3D (float duration) {
        this.duration = duration;
    }

    protected boolean delegate (float delta) {
        if (time < duration) {
            time += delta;
            if (time < duration) return false;
            delta = time - duration;
        }
        if (action == null) return true;
        return action.act(delta);
    }

    /** Causes the delay to be complete. */
    public void finish () {
        time = duration;
    }

    public void restart () {
        super.restart();
        time = 0;
    }

    /** Gets the time spent waiting for the delay. */
    public float getTime () {
        return time;
    }

    /** Sets the time spent waiting for the delay. */
    public void setTime (float time) {
        this.time = time;
    }

    public float getDuration () {
        return duration;
    }

    /** Sets the length of the delay in seconds. */
    public void setDuration (float duration) {
        this.duration = duration;
    }
}
