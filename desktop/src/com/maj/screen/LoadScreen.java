package com.maj.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.google.gwt.editor.rebind.model.ModelUtils;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.d3.actor.BaseActor3DGroup;
import com.kw.gdx.d3.actor.ModelActor3D;
import com.kw.gdx.d3.asset.Asset3D;
import com.kw.gdx.d3.screen.BaseScreen3D;
import com.maj.data.ReadData;
import com.maj.view.LevelItem;

/**
 * Author by tony
 * Date on 2025/6/28.
 */
public class LoadScreen extends BaseScreen3D {
    private ReadData data;

    public LoadScreen(BaseGame game) {
        super(game);
    }

    /**
     *
     000000000000000000000000000000000000
     000000000000000000000000000000000000
     000000000000100001000010000000000000
     000000000000000100010000000000000000
     000000000010100001000010100000000000
     000000000000000100010000000000000000
     000000000001000000000001000000000000
     000000000000000000000000000000000000
     000000000001000000000001000000000000
     000000000000000100010000000000000000
     000000000010100001000010100000000000
     000000000000000100010000000000000000
     000000000000100001000010000000000000
     000000000000000000000000000000000000
     000000000000000000000000000000000000
     */
    @Override
    public void initView() {
        super.initView();
        data = ReadData.getReadData();
        data.read();
        Table table = new Table(){{
            int index = 1;
            for (int i = 0; i < data.getAllLevel().length; i++) {
                LevelItem levelItem = new LevelItem(i);
                add(levelItem);
                if (index % 5 == 0){
                    row();
                    index = 0;
                }
                index ++;
            }
        }};
        ScrollPane pane = new ScrollPane(table);
        pane.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(pane);
    }
}
