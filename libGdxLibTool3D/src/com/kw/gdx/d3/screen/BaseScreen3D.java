package com.kw.gdx.d3.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.kw.gdx.BaseGame;
import com.kw.gdx.d3.stage.Stage3D;
import com.kw.gdx.screen.BaseScreen;

/**
 * 在BaseScreen上的增强
 * <p>
 * 1.添加了Stage3d的支持
 * 2.鼠标点击
 */
public abstract class BaseScreen3D extends BaseScreen {

    protected Stage3D stage3D;

    public BaseScreen3D(BaseGame game) {
        super(game);
        this.stage3D = new Stage3D();
        InputMultiplexer multiplexer = getMultiplexer();
        multiplexer.addProcessor(stage3D);
        multiplexer.addProcessor(stage3D.getCamController());
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void touchEnable() {
        Gdx.input.setInputProcessor(getMultiplexer());
    }

    @Override
    public void render(float delta) {
        //模型在下  ui在上
        //stage3D绘制是否对ui的act有影响？
        stage3D.act(delta);
        stage3D.draw();
        super.render(delta);
    }
}