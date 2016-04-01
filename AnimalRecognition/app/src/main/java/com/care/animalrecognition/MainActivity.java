package com.care.animalrecognition;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.care.adapters.ScreenSlidePagerAdapter;
import com.care.core.AnimalDataManager;
import com.care.core.Constants;
import com.care.core.SharedDataManager;
import com.care.core.Utilities;
import com.care.transforms.ZoomOutPageTransformer;
import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdBannerListener;
import com.flurry.android.ads.FlurryAdErrorType;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayerMenu = null;
    private MediaPlayer mMediaPlayerNext = null;

    private RelativeLayout mFlurryAdBannerLayout = null;
    private FlurryAdBanner mFlurryAdBanner = null;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private PopupWindow mPopupMenu = null;
    private TextView mBkPickTextView = null;
    private TextView mToolbarTextView = null;
    private TextView mRateUsTextView = null;

    private RelativeLayout mMainActitity;
    private ImageView mBackImageView;
    private ImageView mForwardImageView;
    private ImageView mMenuImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActitity = (RelativeLayout) findViewById(R.id.id_main_activity);
        // Instantiate a ViewPager and a PagerAdapter.
        mBackImageView = (ImageView) findViewById(R.id.id_back);
        mForwardImageView = (ImageView) findViewById(R.id.id_forward);
        mMenuImageView = (ImageView) findViewById(R.id.id_btn_menu);
        mPager = (ViewPager) findViewById(R.id.id_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        initBackground();
        // Flurry Initialize
        mFlurryAdBannerLayout = (RelativeLayout) findViewById(R.id.id_flurry_ad_banner);
        mFlurryAdBanner = new FlurryAdBanner(this, mFlurryAdBannerLayout, Constants.FlurryAdSpace);
        mFlurryAdBanner.setListener(new FlurryAdBannerListener() {
            @Override
            public void onFetched(FlurryAdBanner flurryAdBanner) {
                flurryAdBanner.displayAd();
            }

            @Override
            public void onRendered(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onShowFullscreen(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onCloseFullscreen(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onAppExit(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onClicked(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onVideoCompleted(FlurryAdBanner flurryAdBanner) {
            }

            @Override
            public void onError(FlurryAdBanner flurryAdBanner, FlurryAdErrorType flurryAdErrorType, int i) {
                flurryAdBanner.destroy();
            }
        });

        mMediaPlayerMenu = MediaPlayer.create(this, R.raw.btn_sound_1);
        mMediaPlayerMenu.setLooping(false);
        mMediaPlayerNext = MediaPlayer.create(this, R.raw.btn_sound_2);
        mMediaPlayerNext.setLooping(false);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backward();
            }
        });

        mForwardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward();
            }
        });

        initPopupMenu(this);
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
        if(mFlurryAdBanner != null) {
            mFlurryAdBanner.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        initBackground();
        FlurryAgent.onStartSession(this);
        mFlurryAdBanner.fetchAd();
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
        mMainActitity.setBackgroundResource(Utilities.getResId("bk" + (indexBK + 1), R.drawable.class));
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
