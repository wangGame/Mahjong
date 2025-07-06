package com.maj.screen;

import com.badlogic.gdx.graphics.g3d.Model;
import com.kw.gdx.BaseGame;
import com.kw.gdx.d3.actor.BaseActor3DGroup;
import com.kw.gdx.d3.actor.ModelActor3D;
import com.kw.gdx.d3.asset.Asset3D;
import com.kw.gdx.d3.screen.BaseScreen3D;

/**
 * Author by tony
 * Date on 2025/7/6.
 */
public class NewGameScreen extends BaseScreen3D {
    public NewGameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();

        BaseActor3DGroup actor3DGroup = new BaseActor3DGroup();
        stage3D.addActor(actor3DGroup);
        {
            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(-5, 0, 0);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(0, 0, 0);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(5, 0, 0);
            }


            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(0, 0, 5);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(5, 0, -5);
            }
        }

        {
            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(-5, 5, 0);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(0, 5, 0);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(5, 5, 0);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(0, 5, -5);
            }

            {
                Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
                ModelActor3D modelActor3D = new ModelActor3D(model);
                actor3DGroup.addActor3D(modelActor3D);
                modelActor3D.setPosition(0, 5, 5);
            }
        }
    }
}
