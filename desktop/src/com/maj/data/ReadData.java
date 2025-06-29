package com.maj.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.resource.csvanddata.CsvReader;
import com.kw.gdx.resource.csvanddata.demo.CsvUtils;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class ReadData {
    private int cengLine = 15;
    private String[] allLevel;
    public void read(){
        FileHandle internal = Gdx.files.internal("leveldata/LevelData");
        String levelContent = internal.readString();
        levelContent = levelContent.replace("\r\n", "\n");
        allLevel = levelContent.split("\n");
    }

    public static ReadData readData;

    public static ReadData getReadData() {
        if (readData == null) {
            readData = new ReadData();
        }
        return readData;
    }

    public ArrayMap<Integer, Array<String>>  readLevel(int level){
        ArrayMap<Integer, Array<String>> levelData = new ArrayMap<>();
        String levelContent = allLevel[level];
        String contentSplit[] = levelContent.split(",");
        int row = contentSplit.length / cengLine;
        for (int i = 0; i < 9; i++) {
            Array<String> strings = new Array<>();
            boolean flag = false;
            for (int i1 = 0; i1 < 15; i1++) {
                String string = contentSplit[i * 15 + i1];
                if (string.contains("1")) {
                    flag = true;
                }
                strings.add(string);
            }
            if (flag) {
                levelData.put(i, strings);
            }else {
                break;
            }
        }
        return levelData;
    }

    public String[] getAllLevel() {
        return allLevel;
    }
}
