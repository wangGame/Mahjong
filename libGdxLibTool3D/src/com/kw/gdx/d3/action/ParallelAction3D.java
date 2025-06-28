package com.kw.gdx.d3.action;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.kw.gdx.d3.actor.BaseActor3D;

public class ParallelAction3D extends Action3D {
    Array<Action3D> actions = new Array(4);
    private boolean complete;

    public ParallelAction3D () {
    }

    public boolean act (float delta) {
        if (complete) return true;
        complete = true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executing.
        try {
            Array<Action3D> actions = this.actions;
            for (int i = 0, n = actions.size; i < n && actor != null; i++) {
                Action3D currentAction = actions.get(i);
                if (currentAction.getActor() != null && !currentAction.act(delta)) complete = false;
                if (actor == null) return true; // This action was removed.
            }
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        complete = false;
        Array<Action3D> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).restart();
    }

    public void reset () {
        super.reset();
        actions.clear();
    }

    public void addAction (Action3D action) {
        actions.add(action);
        if (actor != null) action.setActor3D(actor);
    }

    public void setActor3D (BaseActor3D actor) {
        Array<Action3D> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).setActor3D(actor);
        super.setActor3D(actor);
    }

    public Array<Action3D> getActions () {
        return actions;
    }

    public String toString () {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append('(');
        Array<Action3D> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++) {
            if (i > 0) buffer.append(", ");
            buffer.append(actions.get(i));
        }
        buffer.append(')');
        return buffer.toString();
    }
}
