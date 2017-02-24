package com.example.aplication.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by k-par_000 on 24.02.2017.
 */
public class gameActivityNum1 extends AbstractGameActivity {

    private static final String TAG = "Game Activity #1";

    @Override
    void update(String s) {

        try (JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8"))))) {

            reader.beginObject();
            //todo remake

            String data = "Empty";
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "status":

                        if (reader.nextString().toLowerCase().equals("ok")) {
                            return;
                        }
                        break;
                    case "version":
                        reader.nextString();
                        break;
                    case "parameters":
                        data = reader.nextString();
                }

            }
            setContentView(R.layout.first_shame);
            ((TextView)findViewById(R.id.textView1)).setText(data);


            Log.d(TAG, "Parsing competed");
        } catch (Exception e) {
            Log.d(TAG,"Json parsing failed");
        }

    }
}
`