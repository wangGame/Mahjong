package com.kw.gdx.d3.action;


import com.badlogic.gdx.utils.Pool;

public class SequenceAction3D extends ParallelAction3D {
    private int index;

    public SequenceAction3D() {
    }

    public SequenceAction3D(Action3D... action1) {
        for (Action3D action : action1) {
            addAction(action);
        }
    }


    public boolean act (float delta) {
        if (index >= actions.size) return true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executings.
        try {
            if (actions.get(index).act(delta)) {
                if (actor == null) return true; // This action was removed.
                index++;
                if (index >= actions.size) return true;
            }
            return false;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        super.restart();
        index = 0;
    }
}
