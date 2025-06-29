package com.maj.data;

import com.badlogic.gdx.utils.Array;
import com.maj.view.MahJItem;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class LevelLogic {
    public Array<MahJItem> getTowItems(Array<MahJItem> mahJItems){
        Array<MahJItem> copyItems = new Array<>();
        Array<MahJItem> outMaj = new Array<>();

        copyItems.addAll(mahJItems);
        if (mahJItems.size>=3) {
            {
                int size = copyItems.size;
                int index = (int) (Math.random() * (size - 2) + 1);
                outMaj.add(copyItems.get(index));
                copyItems.removeIndex(index);
            }
            {
                MahJItem random = copyItems.random();
                outMaj.add(random);
            }
        }else {
            outMaj.addAll(copyItems);
        }
        return outMaj;
    }
}
