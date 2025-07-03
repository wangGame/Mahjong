package com.maj.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.d3.action.Action3D;
import com.kw.gdx.d3.action.Action3Ds;
import com.kw.gdx.d3.actor.BaseActor3D;
import com.kw.gdx.d3.actor.BaseActor3DGroup;
import com.kw.gdx.d3.actor.ModelActor3D;
import com.kw.gdx.d3.asset.Asset3D;
import com.kw.gdx.d3.screen.BaseScreen3D;
import com.kw.gdx.view.dialog.base.BaseDialog;
import com.maj.constant.MajConstant;
import com.maj.data.LevelLogic;
import com.maj.data.ReadData;
import com.maj.utils.MahjongUtils;
import com.maj.view.MahJItem;

import java.util.List;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class GameScreen extends BaseScreen3D {
    private MahJItem selectActor;
    private MahJItem mahJItem[][][];
    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();
        {
            Model bgModel = Asset3D.getAsset3D().getModel("maj/table.g3db");
            ModelActor3D tableActor3D = new ModelActor3D(bgModel);
            stage3D.addActor(tableActor3D);
            tableActor3D.setPosition(0, -0.5f, 0);
            tableActor3D.setScale(6, 1, 6);

            Texture woodTexture = Asset.getAsset().getTexture("maj/wood.png");
            woodTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            woodTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            tableActor3D.setMaterialTexture(woodTexture);
        }
        {
            Model bgModel = Asset3D.getAsset3D().getModel("maj/teacup.g3db");
            ModelActor3D tableActor3D = new ModelActor3D(bgModel);
            stage3D.addActor(tableActor3D);
            tableActor3D.setPosition(0, -0.5f, -15);


            Texture woodTexture = Asset.getAsset().getTexture("maj/Plate_AlbedoTransparency.png");
            woodTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            woodTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            tableActor3D.setMaterialTexture(woodTexture);
        }


        {
            Model bgModel = Asset3D.getAsset3D().getModel("maj/teapot.g3db");
            ModelActor3D tableActor3D = new ModelActor3D(bgModel);
            stage3D.addActor(tableActor3D);
            tableActor3D.setPosition(0, -0.5f, 15);


            Texture woodTexture = Asset.getAsset().getTexture("maj/Plate_AlbedoTransparency.png");
            woodTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            woodTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            tableActor3D.setMaterialTexture(woodTexture);
        }






        ReadData readData = ReadData.getReadData();
        ArrayMap<Integer, Array<String>> entries = readData.readLevel(MajConstant.levelIndex);
        Object[] values = entries.values;
        BaseActor3DGroup gr = new BaseActor3DGroup();
        stage3D.addActor(gr);
        gr.setPosition(0,0,0);
        Array<MahJItem> tilesAll = new Array<>();
        mahJItem = new MahJItem[9][15][36];

        for (int i3 = 0; i3 < entries.size; i3++) {
            Array<String> value1 = (Array<String>) values[i3];
            if (value1 instanceof Array) {
                Array<String> value = (Array<String>) value1;
                for (int i1 = 0; i1 < value.size; i1++) {
                    String s = value.get(i1);
                    System.out.println(s);
                    for (int i2 = 0; i2 < s.length(); i2++) {
                        if (s.charAt(i2) == '1') {
                            MahJItem model = createModel(i2, i1, i3);
                            gr.addActor3D(model);
                            tilesAll.add(model);
                            mahJItem[i3][i1][i2] = model;
                        }
                    }
                }
            }
        }

        Array<MahJItem> tempAll = new Array<>(tilesAll);
        LevelLogic levelLogic = new LevelLogic();
        int suit = 0;
        while (tempAll.size>0) {
            Array<MahJItem> towItems = levelLogic.getTowItems(tempAll);
            for (MahJItem towItem : towItems) {
                towItem.setSuit(suit);
            }
            suit++;
            tempAll.removeAll(towItems,false);
        }

        for (MahJItem[][] mahJItems : mahJItem) {
            System.out.println("==============================");
            for (MahJItem[] jItem : mahJItems) {
                System.out.println();
                for (MahJItem item : jItem) {
                    if (item!=null) {
                        System.out.print(item);
                    }else {
                        System.out.print("      ");
                    }
                }
            }
            System.out.println();
        }
        MahjongUtils.getClickableTiles(mahJItem);

    }


    public MahJItem createModel(int posx,int posy,int posz){
        Model model = Asset3D.getAsset3D().getModel("maj/mahjong_tile.g3db");
        MahJItem actor3D = new MahJItem(model){
            @Override
            public void touchUp(Vector3 vector3, int pointer, int button) {
                super.touchUp(vector3, pointer, button);
                if (selectActor == null){
                    if (!isCanTouch())return;
                    selectActor = this;
                    setColor(Color.GRAY);
                }else {
                    if (!isCanTouch())return;
                    if (selectActor == this){
                        setColor(Color.WHITE);
                        return;
                    }
                    if (selectActor.getSuit() != this.getSuit()){
                        selectActor.setColor(Color.WHITE);
                        this.setColor(Color.WHITE);
                        selectActor = null;
                        return;
                    }
                    selectActor.setMajNull(mahJItem);
                    setMajNull(mahJItem);
                    MahjongUtils.getClickableTiles(mahJItem);
                    selectActor.setColor(Color.WHITE);
                    Vector3 position1 = selectActor.getPosition();
                    Vector3 position2 = getPosition();
                    float xx = (position1.x + position2.x) / 2f;
                    float yy = (position1.z + position2.z) / 2f;

                    if (position1.x<position2.x){
                        selectActor.addAction(
                                Action3Ds.sequenceAction3D(
                                        Action3Ds.moveToAction3D(   position1.x - 1.5f,5,yy,0.2f, Interpolation.fastSlow),
                                        Action3Ds.delay3D(0.1f),
                                        Action3Ds.moveToAction3D(   xx-1,5,yy,0.0333f, Interpolation.slowFast),
                                        Action3Ds.color3D(0,0.2f),
                                        Action3Ds.remove3D()
                                )
                        );
                        this.addAction(
                                Action3Ds.sequenceAction3D(
                                        Action3Ds.moveToAction3D(          position2.x+ 1.5f,5,yy,0.2f, Interpolation.fastSlow),
                                        Action3Ds.delay3D(0.1f),
                                        Action3Ds.moveToAction3D(          xx+ 1,5,yy,0.0333f, Interpolation.slowFast),
                                        Action3Ds.color3D(0,0.2f),
                                        Action3Ds.remove3D()
                                )
                        );
                    }else {
                        selectActor.addAction(
                                Action3Ds.sequenceAction3D(
                                        Action3Ds.moveToAction3D(   position1.x + 1.5f,5,yy,0.2f, Interpolation.fastSlow),
                                        Action3Ds.delay3D(0.1f),
                                        Action3Ds.moveToAction3D(   xx+1,5,yy,0.0333f, Interpolation.exp5In),
                                        Action3Ds.color3D(00,0.2f),
                                        Action3Ds.remove3D()
                                )
                        );
                        this.addAction(
                                Action3Ds.sequenceAction3D(
                                        Action3Ds.moveToAction3D(          position2.x- 1.5f,5,yy,0.2f, Interpolation.fastSlow),
                                        Action3Ds.delay3D(0.1f),
                                        Action3Ds.moveToAction3D(          xx- 1,5,yy,0.0333f, Interpolation.slowFast),
                                        Action3Ds.color3D(0,0.2f),
                                        Action3Ds.remove3D()
                                )
                        );
                    }
                    selectActor = null;
                }
            }
        };
        actor3D.setArrPos(posx,posy,posz);
        actor3D.setScale(1,1,1);
        actor3D.setPosition((posx- 18)*1,posz*1,(posy-8)*1.4f - 1.4f*posz);
        BoundingBox boundingBox = new BoundingBox();
        actor3D.getModel().calculateBoundingBox(boundingBox);
        return actor3D;
    }

    @Override
    protected BaseDialog back() {
        BaseDialog back = super.back();
        if (back == null) {
            setScreen(LoadScreen.class);
        }
        return back;
    }
}
