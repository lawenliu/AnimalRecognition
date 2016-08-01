package com.care.animalrecognition;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.care.adapters.ScreenSlidePagerAdapter;
import com.care.core.Constants;
import com.care.core.SharedDataManager;
import com.care.core.Utilities;
import com.care.transforms.ZoomOutPageTransformer;
import com.flurry.android.FlurryAgent;
import com.wandoujia.ads.sdk.Ads;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayerMenu = null;
    private MediaPlayer mMediaPlayerNext = null;

    private RelativeLayout mAdBannerLayout = null;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private PopupWindow mPopupMenu = null;
    private TextView mBkPickTextView = null;
    private TextView mToolbarTextView = null;
    private TextView mRateUsTextView = null;

    private RelativeLayout mMainActivity = null;
    private ImageView mMenuImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivity = (RelativeLayout) findViewById(R.id.id_main_activity);
        // Instantiate a ViewPager and a PagerAdapter.
        mMenuImageView = (ImageView) findViewById(R.id.id_btn_menu);
        mPager = (ViewPager) findViewById(R.id.id_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        initBackground();
        // Flurry Initialize
        mAdBannerLayout = (RelativeLayout) findViewById(R.id.id_ad_banner);

        mMediaPlayerMenu = MediaPlayer.create(this, R.raw.btn_sound_1);
        mMediaPlayerMenu.setLooping(false);
        mMediaPlayerNext = MediaPlayer.create(this, R.raw.btn_sound_2);
        mMediaPlayerNext.setLooping(false);

        initPopupMenu(this);
        initializeAds();
    }

    private void initializeAds() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Ads.init(MainActivity.this, Constants.WANDOUJIA_APP_ID, Constants.WANDOUJIA_SECRET_KEY);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Ads.preLoad(Constants.WANDOUJIA_BANNER_ID, Ads.AdFormat.banner);
                    View bannerView = Ads.createBannerView(MainActivity.this, Constants.WANDOUJIA_BANNER_ID);
                    mAdBannerLayout.addView(bannerView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                }
            }
        }.execute();
    }

    private void backward() {
        mMediaPlayerNext.start();
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    private void forward() {
        mMediaPlayerNext.start();
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        initBackground();
        FlurryAgent.onStartSession(this);
    }

    @Override
    public void onStop() {
        FlurryAgent.onEndSession(this);

        super.onStop();
    }

    public void startAnimalRecognitionService() {
        try {
            Intent intent = new Intent(MainActivity.this, AnimalRecognitionService.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAnimalRecognitionService() {
        try {
            Intent intent = new Intent(MainActivity.this, AnimalRecognitionService.class);
            stopService(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            backward();
        }
    }

    private void initBackground() {
        int indexBK = SharedDataManager.getInstance().getCurrentBackgroundIndex();
        mMainActivity.setBackgroundResource(Utilities.getResId("bk" + (indexBK + 1), R.drawable.class));
    }

    private void initPopupMenu(final Context context) {
        View menuContent = View.inflate(this, R.layout.menu_popup, null);
        mBkPickTextView = (TextView)menuContent.findViewById(R.id.menu_color_screen);
        mToolbarTextView = (TextView)menuContent.findViewById(R.id.menu_tool_bar);
        mRateUsTextView = (TextView)menuContent.findViewById(R.id.menu_rate_us);

        mPopupMenu = new PopupWindow(menuContent, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupMenu.setFocusable(true);
        mPopupMenu.setOutsideTouchable(true);
        mPopupMenu.setBackgroundDrawable(new ColorDrawable());
        mPopupMenu.update();

        mMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                mMediaPlayerMenu.start();
            }
        });
        mBkPickTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                Intent intent = new Intent(context, BkImageSelectActivity.class);
                startActivity(intent);
            }
        });
        mToolbarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRateUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                Utilities.launchAppStoreDetail(context);
            }
        });
    }


    private void showAndDismissPopupMenu() {
        if(mPopupMenu.isShowing()) {
            mPopupMenu.dismiss();
        } else {
            mPopupMenu.showAsDropDown(mMenuImageView);
        }
    }
}
