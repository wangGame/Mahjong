package com.maj.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.resource.csvanddata.CsvReader;
import com.kw.gdx.resource.csvanddata.demo.CsvUtils;

/**
 * Author by tony
 * Date on 2025/6/29.
 */
public class ReadData {
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

    public String readLevel(int level){
        return allLevel[level];
    }
}
