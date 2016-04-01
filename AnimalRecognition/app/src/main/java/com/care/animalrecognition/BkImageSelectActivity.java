package com.care.animalrecognition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.care.adapters.ImageAdapter;
import com.care.core.SharedDataManager;

/**
 * Created by wliu37 on 3/30/2016.
 */
public class BkImageSelectActivity extends Activity {

    private Button mBkSelectButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bk_image_select);

        mBkSelectButton = (Button) findViewById(R.id.id_bk_select_btn);
        GridView gridView = (GridView) findViewById(R.id.id_bk_image_gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                SharedDataManager.getInstance().setCurrentBackgroundIndex(position);
                mBkSelectButton.setText(R.string.enter);
                Toast.makeText(BkImageSelectActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
