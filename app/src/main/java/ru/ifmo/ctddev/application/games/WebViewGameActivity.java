package ru.ifmo.ctddev.application.games;

import android.util.JsonReader;
import android.util.Log;
import android.webkit.WebView;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import ru.ifmo.ctddev.application.R;
import ru.ifmo.ctddev.application.database.Game;


public class WebViewGameActivity extends AbstractGameActivity {

    private static final String TAG = "Game Activity #1";

    @Override
    Game gameInfoUpdate(String s) {

        Game answewr = new Game();
        answewr.setId(gameId);

        try (JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8"))))) {

            reader.beginObject();

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
            Log.d(TAG, "Json parsing failed");
        }

        return answewr;
    }

    @Override
    void updateView(Game game) {
        setContentView(R.layout.web_view_game);
        ((WebView) findViewById(R.id.webView)).loadData(game.getDescr(), "text/html", "UTF-8");
    }
}
