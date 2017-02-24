package com.example.aplication.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by k-par_000 on 24.02.2017.
 */
public class gameActivityNum1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.menu);
        updateGame((TextView) findViewById(R.id.textView1));


    }

    private void updateGame(TextView textView) {
        try {
            Uri uri = Uri.parse("http://pizdyk.000webhostapp.com/").buildUpon().appendQueryParameter("command", "game").appendQueryParameter("id","1").appendQueryParameter("version", "0").build();
            URL url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream()));


            reader.beginObject();
            reader.nextName();
            String s = reader.nextString();
            if (!s.toLowerCase().equals("ok")) {
                reader.nextName();
                reader.nextString();
                reader.nextName();
                textView.setText(reader.nextString());
            }
            reader.close();
        } catch (IOException e) {

        }

    }

}
