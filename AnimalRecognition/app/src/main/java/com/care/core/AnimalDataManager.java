package com.care.core;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by juily on 2016/3/26.
 */
public class AnimalDataManager {

    private List<AnimalInfo> animalInfos = new ArrayList<AnimalInfo>();
    private static AnimalDataManager instance = null;

    public static AnimalDataManager getInstance(Context context) {
        if(instance != null) {
            return instance;
        }

        instance = new AnimalDataManager();
        instance.DataParser(context);
        return instance;
    }

    public int getAnimalCount() {
        return animalInfos.size();
    }

    public AnimalInfo getAnimalByIndex(int index) {
        int test = getAnimalCount();
        if(index < 0 && index >= getAnimalCount()) {
            return null;
        }

        return animalInfos.get(index);
    }

    private void DataParser(Context context) {
        JSONParser jsonParser = new JSONParser();
       //  JSONObject jsonObject = jsonParser.getJSONFromUrl();

        JSONObject jsonObject  = jsonParser.getJSONFromRaw(context);
        if(jsonObject == null) {
            return;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("animals");
            for(int index = 0; index <= jsonArray.length(); index++) {
                JSONObject animalObject = jsonArray.getJSONObject(index);
                AnimalInfo animalInfo = new AnimalInfo();
                animalInfo.Name = animalObject.getString("Name");
                animalInfo.Pinyin = animalObject.getString("Pinyin");
                animalInfo.English = animalObject.getString("English");
                animalInfo.PronUS = animalObject.getString("PronUS");
                animalInfo.AudioUS = animalObject.getString("AudioUS");
                animalInfo.PronUK = animalObject.getString("PronUK");
                animalInfo.AudioUK = animalObject.getString("AudioUK");
                animalInfo.Image = animalObject.getString("Image");
                animalInfo.EnDspt = animalObject.getString("EnDspt");
                animalInfo.ZhDspt = animalObject.getString("ZhDspt");
                animalInfo.Sounds = new ArrayList<String>();
                JSONArray soundArray = animalObject.getJSONArray("Sounds");
                if(soundArray != null) {
                    for (int soundIndex = 0; soundIndex < soundArray.length(); soundIndex++) {
                        String soundObject = soundArray.getString(soundIndex);
                        animalInfo.Sounds.add(soundObject);
                    }
                }

                animalInfos.add(animalInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
