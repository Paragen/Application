package com.example.aplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by k-par_000 on 24.02.2017.
 */
public abstract class AbstractGameActivity extends Activity implements DownloadCallback {

    private AsyncLoader loader;
    private static final String TAG = "Abstract Game Activity";
    public static final String ARG_STR = "ID";
    protected Game currentGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new AsyncLoader(this);

        Intent intent = getIntent();
        int gameId = intent.getIntExtra(ARG_STR, 0);

        currentGame = new DBHelper(this).getGame(gameId, 0);
        updateView(currentGame);

        loader.execute("command=game&id=" + Integer.toString(gameId) + "&version=" + Integer.toString(currentGame.getVersion()));
    }



    @Override
    public void onLoadComplete(String data) {
        Game game = gameInfoUpdate(data);
        if (game != null) {
            updateView(game);
            new DBHelper(this).setGame(game);
        }
    }

    // parse server reply . Return Game if new info received  and null otherwise
    abstract Game gameInfoUpdate(String jsonData);

    //update views
    abstract void updateView(Game game);

}
