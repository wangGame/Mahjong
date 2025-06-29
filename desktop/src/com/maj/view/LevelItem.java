package com.maj.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.maj.constant.MajConstant;
import com.maj.screen.GameScreen;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class LevelItem extends Group {
    public LevelItem(int index){
        setSize(100,100);
        Label label = new Label(index+"",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("font/Manrope-ExtraBold_40_1.fnt");
        }});
        label.setPosition(50,50, Align.center);
        addActor(label);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MajConstant.levelIndex = index;
                Constant.currentActiveScreen.setScreen(GameScreen.class);
            }
        });
    }
}
