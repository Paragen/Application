package com.example.aplication.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by k-par_000 on 24.02.2017.
 */
public abstract class AbstractGameActivity extends Activity implements DownloadCallback {

    private AsyncLoader loader;
    private static final String TAG = "Abstract Game Activity";
    public static final String ARG_STR = "Url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new AsyncLoader(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra(ARG_STR);
        loader.execute(url);
    }



    @Override
    public void onLoadComplete(String data) {
        update(data);
    }

    abstract void update(String jsonData);
}
