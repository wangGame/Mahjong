package com.kw.gdx.d3.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class IntAction3D extends TemporalAction3D {
    private int start, end;
    private int value;

    /** Creates an IntAction that transitions from 0 to 1. */
    public IntAction3D () {
        start = 0;
        end = 1;
    }

    /** Creates an IntAction that transitions from start to end. */
    public IntAction3D (int start, int end) {
        this.start = start;
        this.end = end;
    }

    /** Creates a FloatAction that transitions from start to end. */
    public IntAction3D (int start, int end, float duration) {
        super(duration);
        this.start = start;
        this.end = end;
    }

    /** Creates a FloatAction that transitions from start to end. */
    public IntAction3D (int start, int end, float duration, Interpolation interpolation) {
        super(duration, interpolation);
        this.start = start;
        this.end = end;
    }

    protected void begin () {
        value = start;
    }

    protected void update (float percent) {
        if (percent == 0)
            value = start;
        else if (percent == 1)
            value = end;
        else
            value = (int)(start + (end - start) * percent);
        System.out.println(value);
    }

    /** Gets the current int value. */
    public int getValue () {
        return value;
    }

    /** Sets the current int value. */
    public void setValue (int value) {
        this.value = value;
    }

    public int getStart () {
        return start;
    }

    /** Sets the value to transition from. */
    public void setStart (int start) {
        this.start = start;
    }

    public int getEnd () {
        return end;
    }

    /** Sets the value to transition to. */
    public void setEnd (int end) {
        this.end = end;
    }
}
