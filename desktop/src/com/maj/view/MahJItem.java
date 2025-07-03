package com.maj.view;

import com.badlogic.gdx.graphics.Color;
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
    private boolean canTouch;

    public MahJItem(Model model) {
        super(model);
    }

    public void setSuit(int suit) {
        this.suit = suit;

        suit = suit % 33 +1;
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

    private int posx;
    private int posy;
    private int posz;
    public void setArrPos(int posx, int posy, int posz) {
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getPosz() {
        return posz;
    }

    public void setPosz(int posz) {
        this.posz = posz;
    }

    public void setMajNull(MahJItem[][][] mahJItem) {
        mahJItem[posz][posy][posx] = null;
    }

    public void setCannotMove() {
        setColor(Color.BLACK);
        canTouch = false;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanMove(){
        setColor(Color.WHITE);
        canTouch = true;
    }
}
