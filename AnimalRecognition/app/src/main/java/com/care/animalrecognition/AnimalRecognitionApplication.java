package com.care.animalrecognition;

import android.app.Application;

import com.care.core.Constants;
import com.care.core.SharedDataManager;
import com.flurry.android.FlurryAgent;

/**
 * Created by laliu on 2015/8/11.
 */
public class AnimalRecognitionApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Init Flurry
        FlurryAgent.init(this, Constants.FlurryApplicationKey);

        // Init shared data
        SharedDataManager.getInstance().initialize(this);
    }
}
