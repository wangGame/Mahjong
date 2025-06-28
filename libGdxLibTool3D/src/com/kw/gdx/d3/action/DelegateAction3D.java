package com.kw.gdx.d3.action;

import com.badlogic.gdx.utils.Pool;
import com.kw.gdx.d3.actor.BaseActor3D;

public abstract class DelegateAction3D extends Action3D {
    protected Action3D action;

    /** Sets the wrapped action. */
    public void setAction (Action3D action) {
        this.action = action;
    }

    public Action3D getAction () {
        return action;
    }

    abstract protected boolean delegate (float delta);

    public final boolean act (float delta) {
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool inside the delegate action.
        try {
            return delegate(delta);
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        if (action != null) action.restart();
    }

    public void reset () {
        super.reset();
        action = null;
    }

    public void setActor3D (BaseActor3D actor) {
        if (action != null) action.setActor3D(actor);
        super.setActor3D(actor);
    }

    public void setTarget (BaseActor3D target) {
        if (action != null) action.setTarget(target);
        super.setTarget(target);
    }

    public String toString () {
        return super.toString() + (action == null ? "" : "(" + action + ")");
    }
}
