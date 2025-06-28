package com.maj.data;

import com.badlogic.gdx.utils.Array;
import com.maj.view.MahJItem;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class LevelLogic {
    public void getTowItems(Array<MahJItem> mahJItems){
        Array<MahJItem> copyItems = new Array<>();
        Array<MahJItem> outMaj = new Array<>();
        Array<MahJItem> outMajGHz = new Array<>();
        if (mahJItems.size>=3) {
            {
                int size = outMaj.size;
                int index = (int) (Math.random() * (size - 1) + 1);
                outMajGHz.add(copyItems.get(index));
                copyItems.removeIndex(index);
            }
            {
                MahJItem random = copyItems.random();
                outMaj.add(random);
            }
        }
    }
}
