package com.care.adapters;

/**
 * Created by wliu37 on 3/31/2016.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.care.animalrecognition.SlidePageFragment;
import com.care.core.AnimalDataManager;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private int mNumberPages = 0;

    public ScreenSlidePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mNumberPages = AnimalDataManager.getInstance(context).getAnimalCount();
    }

    @Override
    public Fragment getItem(int position) {
        SlidePageFragment fragment = new SlidePageFragment();
        fragment.setArguments(mContext, position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumberPages;
    }
}