package ru.ifmo.ctddev.application.games;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import ru.ifmo.ctddev.application.database.DBHelper;
import ru.ifmo.ctddev.application.database.Game;
import ru.ifmo.ctddev.application.util.AsyncLoader;
import ru.ifmo.ctddev.application.util.DownloadCallback;

/**
 * Created by k-par_000 on 24.02.2017.
 */
public abstract class AbstractGameActivity extends Activity implements DownloadCallback {

    private AsyncLoader loader;
    private static final String TAG = "Abstract Game Activity";
    public static final String ARG_STR = "ID";
    protected Game currentGame;
    protected int gameId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new AsyncLoader(this);

        Intent intent = getIntent();
        gameId = intent.getIntExtra(ARG_STR, 0);

        welcome();

        currentGame = new DBHelper(this).getGame(gameId, 0);

        int currentVersion = 0;
        if (currentGame != null) {
            updateView(currentGame);
            currentVersion = currentGame.getVersion();
        }

        loader.execute("command=static_game&id=" + Integer.toString(gameId) + "&version=" + Integer.toString(currentVersion));
    }


    @Override
    public void onLoadComplete(String data) {
        Game game = gameInfoUpdate(data);
        if (game != null) {
            updateView(game);
            new DBHelper(this).setGame(game);
            Log.d(TAG, "Loaded from server");
        } else {
            Log.d(TAG, "Loaded from DB");
        }
    }


    Game gameInfoUpdate(String s) {

        Game answer = new Game();
        answer.setId(gameId);

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
                        answer.setVersion(reader.nextInt());
                        break;
                    case "data":
                        reader.beginArray();
                        List<String> list = new ArrayList<>();

                        while (reader.hasNext()) {
                            list.add(reader.nextString());
                        }

                        reader.endArray();

                        JsonWriter writer = new JsonWriter(new StringWriter());
                        writer.beginArray();
                        for (String str :
                                list) {
                            writer.value(str);
                        }

                        writer.endArray();
                        answer.setDescr(writer.toString());
                }

            }

            Log.d(TAG, "Parsing competed");
        } catch (Exception e) {
            answer = null;
            Log.d(TAG, "Json parsing failed");
        }

        return answer;
    }

    //update views
    protected abstract void updateView(Game game);

    protected void welcome() {
    }

    @Override
    public void onDestroy() {
        loader.cancel(true);
    }
}
