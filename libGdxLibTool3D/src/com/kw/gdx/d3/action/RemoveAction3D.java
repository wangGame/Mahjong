package com.kw.gdx.d3.action;

import com.badlogic.gdx.scenes.scene2d.Action;

public class RemoveAction3D extends Action3D {
    private Action3D action;

    public boolean act (float delta) {
        target.removeAction(action);
        return true;
    }

    public Action3D getAction () {
        return action;
    }

    public void setAction (Action3D action) {
        this.action = action;
    }

    public void reset () {
        super.reset();
        action = null;
    }
}
