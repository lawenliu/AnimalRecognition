package com.care.animalrecognition;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.care.adapters.ImageSelectAdapter;
import com.care.adapters.ScreenSlidePagerAdapter;
import com.care.core.SharedDataManager;
import com.care.core.Utilities;

/**
 * Created by wliu37 on 3/30/2016.
 */
public class BkImageSelectActivity extends Activity {

    private MediaPlayer mMediaPlayerBack = null;
    private MediaPlayer mMediaPlayerImage = null;

    private RelativeLayout mBkSelectActivity;
    private TextView mBkBackButton;
    private GridView mGridView;
    private BaseAdapter mBaseAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bk_image_select);
        mBkSelectActivity = (RelativeLayout) findViewById(R.id.id_bk_image_select_activity);
        mBkBackButton = (TextView) findViewById(R.id.id_bk_back_btn);
        mGridView = (GridView) findViewById(R.id.id_bk_image_gridview);
        mBaseAdapter = new ImageSelectAdapter(this);
        mGridView.setAdapter(mBaseAdapter);

        mMediaPlayerBack = MediaPlayer.create(this, R.raw.btn_sound_1);
        mMediaPlayerBack.setLooping(false);
        mMediaPlayerImage = MediaPlayer.create(this, R.raw.btn_sound_2);
        mMediaPlayerImage.setLooping(false);

        mBkBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayerBack.start();
                finish();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mMediaPlayerImage.start();
                SharedDataManager.getInstance().setCurrentBackgroundIndex(position);
                int indexBK = SharedDataManager.getInstance().getCurrentBackgroundIndex();
                mBkSelectActivity.setBackgroundResource(Utilities.getResId("bk" + (indexBK + 1), R.drawable.class));
            }
        });
    }
}
