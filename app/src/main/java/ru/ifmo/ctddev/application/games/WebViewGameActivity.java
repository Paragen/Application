package ru.ifmo.ctddev.application.games;

import android.webkit.WebView;

import ru.ifmo.ctddev.application.R;
import ru.ifmo.ctddev.application.database.Game;


public class WebViewGameActivity extends DynamicGames {

    private static final String TAG = "Game Activity #1";



    @Override
    protected void updateView(Game game) {
        setContentView(R.layout.web_view_game);
        ((WebView) findViewById(R.id.webView)).loadData(game.getDescr(), "text/html", "UTF-8");
    }
}
