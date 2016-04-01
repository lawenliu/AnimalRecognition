package com.care.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.care.animalrecognition.ImageItemView;
import com.care.animalrecognition.R;

/**
 * Created by wliu37 on 3/30/2016.
 */
public class ImageSelectAdapter extends BaseAdapter {

    private int mNumberPages = 0;
    private Context mContext;

    public ImageSelectAdapter(Context context) {
        super();

        mContext = context;
        mNumberPages = ImageItemView.getThumbNumber();
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItemView imageItemView = (ImageItemView)convertView;

        if (imageItemView == null || imageItemView.getCurrentIndex() != position) {
            imageItemView = new ImageItemView(mContext);
            imageItemView.setArguments(position);
        }

        return imageItemView;
    }

    @Override
    public int getCount() {
        return mNumberPages;
    }
}
