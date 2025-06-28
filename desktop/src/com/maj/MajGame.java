package com.maj;

import com.kw.gdx.BaseGame;
import com.kw.gdx.resource.annotation.GameInfo;
import com.maj.screen.LoadScreen;

/**
 * Author by tony
 * Date on 2025/6/28.
 */
@GameInfo(width =  720,height = 1080)
public class MajGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setScreen(new LoadScreen(this));
    }
}
