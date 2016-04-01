package com.care.animalrecognition;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.care.core.AnimalDataManager;
import com.care.core.AnimalInfo;
import com.care.core.Utilities;
import com.flurry.android.FlurryAgent;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by wliu37 on 3/22/2016.
 */
public class SlidePageFragment extends Fragment {

    private int mCurrentIndex = 0;

    private MediaPlayer mMediaPlayerImage = null;
    private MediaPlayer mMediaPlayerUkAudio = null;
    private MediaPlayer mMediaPlayerUsAudio = null;

    private ImageView mPictureImageView;
    private TextView mZhDescTextView;
    private TextView mZhWordTextView;
    private TextView mZhPronTextView;
    private TextView mEnWordTextView;
    private TextView mUkPronTextView;
    private ImageView mUkPronAudioImageView;
    private TextView mUsPronTextView;
    private ImageView mUsPronAudioImageView;
    private TextView mEnDescTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_page, container, false);

        mPictureImageView = (ImageView) rootView.findViewById(R.id.id_page_picture);
        mZhDescTextView = (TextView) rootView.findViewById(R.id.id__page_zh_desc);
        mZhWordTextView = (TextView) rootView.findViewById(R.id.id_page_zh_word);
        mZhPronTextView = (TextView) rootView.findViewById(R.id.id_page_zh_pron);
        mEnWordTextView = (TextView) rootView.findViewById(R.id.id_page_en_word);
        mUkPronTextView = (TextView) rootView.findViewById(R.id.id_page_uk_pron);
        mUkPronAudioImageView = (ImageView) rootView.findViewById(R.id.id_page_uk_pron_audio);
        mUsPronTextView = (TextView) rootView.findViewById(R.id.id_page_us_pron);
        mUsPronAudioImageView = (ImageView) rootView.findViewById(R.id.id_page_us_pron_audio);
        mEnDescTextView = (TextView) rootView.findViewById(R.id.id_page_en_desc);

        AnimalInfo animalInfo = AnimalDataManager.getInstance(getContext()).getAnimalByIndex(mCurrentIndex);
        mPictureImageView.setImageResource(Utilities.getResId(animalInfo.Image, R.drawable.class));
        mZhDescTextView.setText(animalInfo.ZhDspt);
        mZhWordTextView.setText(animalInfo.Name);
        mZhPronTextView.setText(animalInfo.Pinyin);
        mEnWordTextView.setText(animalInfo.English);
        mUkPronTextView.setText(animalInfo.PronUK);
        mUsPronTextView.setText(animalInfo.PronUS);
        mEnDescTextView.setText((animalInfo.EnDspt));

        mMediaPlayerUkAudio = MediaPlayer.create(getContext(), Utilities.getResId(animalInfo.AudioUK, R.raw.class));
        mMediaPlayerUkAudio.setLooping(false);
        mMediaPlayerUsAudio = MediaPlayer.create(getContext(),  Utilities.getResId(animalInfo.AudioUS, R.raw.class));
        mMediaPlayerUsAudio.setLooping(false);
        Random rand = new Random();
        int index = animalInfo.Sounds.size() > 1 ? rand.nextInt(animalInfo.Sounds.size() - 1) : -1;
        if(index >= 0) {
            mMediaPlayerImage = MediaPlayer.create(getContext(), Utilities.getResId(animalInfo.Sounds.get(index), R.raw.class));
            mMediaPlayerImage.setLooping(false);
        }

        mPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayerImage != null) {
                    mMediaPlayerImage.start();
                }
            }
        });

        mUkPronAudioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayerUkAudio != null) {
                    mMediaPlayerUkAudio.start();
                }
            }
        });

        mUsPronAudioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayerUsAudio != null) {
                    mMediaPlayerUsAudio.start();
                }
            }
        });

        return rootView;
    }

    public void setArguments(int curIndex) {
        mCurrentIndex = curIndex;
    }

    @Override
    public void onStop() {
        stopMediaPlayer();

        super.onStop();
    }

    public void stopMediaPlayer() {
        if(mMediaPlayerImage != null &&
                mMediaPlayerUsAudio != null &&
                mMediaPlayerUkAudio != null) {
            mMediaPlayerImage.stop();
            mMediaPlayerUkAudio.stop();
            mMediaPlayerUsAudio.stop();
        }
    }
}
