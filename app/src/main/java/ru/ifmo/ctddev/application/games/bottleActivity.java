package ru.ifmo.ctddev.application.games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.ifmo.ctddev.application.R;

public class bottleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottle);
    }

    public void back(View view) {
        this.finish();
    }
}
