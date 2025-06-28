package com.kw.gdx.d3.action;

public class AddAction3D extends Action3D {
    private Action3D action;

    public boolean act(float delta) {
        target.addAction(action);
        return true;
    }

    public Action3D getAction() {
        return action;
    }

    public void setAction(Action3D action) {
        this.action = action;
    }

    public void restart() {
        if (action != null) action.restart();
    }

    public void reset() {
        super.reset();
        action = null;
    }
}
