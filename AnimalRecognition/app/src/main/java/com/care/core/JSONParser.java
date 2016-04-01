package com.care.core;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.care.animalrecognition.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by juily on 2016/3/26.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {}

    public JSONObject getJSONFromUrl(String url) {

        try {
            //constants
            URL uri = new URL("http://myhost.com/ajax");

            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            //make some HTTP header nicety
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            //open
            conn.connect();
            //do somehting with response
            is = conn.getInputStream();
            conn.disconnect();
        } catch (Exception ex) {
            Log.e("JsonRequest", ex.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }

    public JSONObject getJSONFromRaw(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.animal_info);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            is.close();
        } catch (Exception ex) {
            Log.e("GetJsonObject", "Failed");
        }

        // try parse the string to a JSON object
        try {
            String jsonString = writer.toString();
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }
}