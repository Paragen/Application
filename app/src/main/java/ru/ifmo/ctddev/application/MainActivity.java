package ru.ifmo.ctddev.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.ifmo.ctddev.application.games.MafiaActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.button)).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }


    public void Mafia(View view) {
        Intent intent = new Intent(this, MafiaActivity.class);
        startActivity(intent);
    }

}
