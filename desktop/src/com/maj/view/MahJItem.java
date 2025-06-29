package com.maj.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.d3.actor.ModelActor3D;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class MahJItem extends ModelActor3D {
    private int suit;

    public MahJItem(Model model) {
        super(model);
    }

    public void setSuit(int suit) {
        this.suit = suit;

        suit = suit % 34;
        Texture woodTexture = Asset.getAsset().getTexture("tile/mahjong_tile_"+suit+".png");
        woodTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        woodTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setMaterialTexture(woodTexture);

    }

    public boolean match(MahJItem mahJItem){
        if (mahJItem.getSuit() == suit) {
            return true;
        }
        return false;
    }

    public int getSuit() {
        return suit;
    }
}
