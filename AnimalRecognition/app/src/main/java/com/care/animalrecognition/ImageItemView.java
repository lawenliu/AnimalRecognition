package com.care.animalrecognition;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.care.core.AnimalDataManager;
import com.care.core.AnimalInfo;
import com.care.core.Utilities;

import java.util.Random;

/**
 * Created by juily on 2016/4/1.
 */
public class ImageItemView extends RelativeLayout {

    public static Integer[] ThumbIds = {
            R.drawable.bk1, R.drawable.bk2,
            R.drawable.bk3, R.drawable.bk4,
            R.drawable.bk5, R.drawable.bk6
    };

    private int mCurrentIndex = 0;

    private ImageView mBkItemImageView;

    public ImageItemView(Context context) {
        super(context);

        InitView(context);
    }

    public void InitView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_image_item, this);

        mBkItemImageView = (ImageView) findViewById(R.id.id_bk_image_item);
    }

    public void setArguments(int curIndex) {
        mCurrentIndex = curIndex;
        mBkItemImageView.setImageResource(ThumbIds[mCurrentIndex]);
    }

    public static int getThumbNumber() {
        return ThumbIds.length;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }
}
