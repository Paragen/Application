package ru.ifmo.ctddev.application.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import ru.ifmo.ctddev.application.R;

public class MafiaRulesActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mafia_rules);

    }

    public void back(View view) {
        this.finish();
    }


}
