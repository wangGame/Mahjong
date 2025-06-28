package com.kw.gdx.d3.action;


public class RemoveActorAction3D extends Action3D {
    private boolean removed;

    public boolean act (float delta) {
        if (!removed) {
            removed = true;
            target.remove();
        }
        return true;
    }

    public void restart () {
        removed = false;
    }
}
