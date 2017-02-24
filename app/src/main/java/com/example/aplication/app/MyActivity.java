package com.example.aplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.button)).setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }

}
