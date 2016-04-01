package com.care.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by laliu on 2015/8/11.
 */
public class SharedDataManager {
    private static SharedDataManager instance = null;

    private SharedPreferences mPrefs = null;
    private Editor mEditor = null;

    public static SharedDataManager getInstance() {
        if(instance == null) {
            instance = new SharedDataManager();
        }

        return instance;
    }

    public void initialize(Context context) {
        mPrefs = context.getSharedPreferences(Constants.SharedPreferenceFileName, Context.MODE_PRIVATE);
        mEditor = this.mPrefs.edit();
    }

    public boolean isAntiMosquitoTurnedOn() {
        return mPrefs != null && this.mPrefs.getBoolean(Constants.KeyAntiMosquitoTurnedOn, false);
    }

    public void setIsAntiMosquitoTurnedOn(boolean newValue) {
        if(mEditor != null) {
            mEditor.putBoolean(Constants.KeyAntiMosquitoTurnedOn, newValue);
            mEditor.commit();
        }
    }

    public int getCurrentBackgroundIndex() {
        if(mPrefs != null) {
            return mPrefs.getInt(Constants.KeyCurrentBackgroundIndex, 0);
        }

        return 0;
    }

    public void setCurrentBackgroundIndex(int newValue) {
        if(mEditor != null) {
            mEditor.putInt(Constants.KeyCurrentBackgroundIndex, newValue);
            mEditor.commit();
        }
    }
}
