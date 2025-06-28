package com.maj;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Author by tony
 * Date on 2025/6/28.
 */
public class MajGameDeck {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 1000;
        config.stencil=8;
        config.y = 0;
        config.height = (int) (1920*0.5f);
        config.width = (int) (1080*0.5f);
        new LwjglApplication(new MajGame(), config);
    }
}
