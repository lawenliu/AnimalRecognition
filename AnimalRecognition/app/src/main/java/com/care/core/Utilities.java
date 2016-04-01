package com.care.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.care.animalrecognition.R;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by laliu on 2015/8/11.
 */
public class Utilities {

    public static void launchAppStoreDetail(Context context) {
        if (context != null) {
            try {
                String link = String.format(Locale.getDefault(), Constants.LinkMarketPackageFormat,
                        context.getPackageName());
                Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(context, context.getString(R.string.message_no_store), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ColumnInfo calculateColumnCountInRow(int screenWidth,int width,int padding){
        ColumnInfo colInfo = new ColumnInfo();
        int colCount = 0;
        int space = screenWidth % width;

        if (space == 0){
            colCount = screenWidth / width;
        } else if( space >= ( width / 2 ) ){
            colCount = screenWidth / width;
            space = width - space;
            width = width + space / colCount;
        } else{
            colCount = screenWidth / width + 1;
            width = width - space / colCount;
        }

        colInfo.countInRow = colCount;

        colInfo.width = width - (( colCount + 1 ) * padding ) / colCount;

        return colInfo;
    }

    public static ColumnInfo calculateColumnWidthInRow(int screenWidth,int count,int padding){
        ColumnInfo colInfo = new ColumnInfo();

        colInfo.countInRow = count;

        colInfo.width = (screenWidth - padding) / count - 2 * padding;

        return colInfo;
    }
}
