package com.example.aplication.app;

import android.util.JsonReader;
import android.util.Log;
import android.webkit.WebView;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;


public class gameActivityNum1 extends AbstractGameActivity {

    private static final String TAG = "Game Activity #1";

    @Override
    Game gameInfoUpdate(String s) {

        Game answewr = new Game();
        answewr.setId(gameId);

        try (JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8"))))) {

            reader.beginObject();
            //todo remake

            String data = "Empty";
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "status":
                        if (reader.nextString().toLowerCase().equals("ok")) {
                            return null;
                        }
                        break;
                    case "version":
                        answewr.setVersion(reader.nextInt());
                        break;
                    case "description":
                        answewr.setDescr(reader.nextString());
                }

            }

            Log.d(TAG, "Parsing competed");
        } catch (Exception e) {
            answewr = null;
            Log.d(TAG,"Json parsing failed");
        }

        return answewr;
    }

    @Override
    void updateView(Game game) {
        setContentView(R.layout.first_shame);
        ((WebView) findViewById(R.id.webView)).loadData(game.getDescr(), "text/html", "UTF-8");
    }
}
