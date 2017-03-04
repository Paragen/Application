package ru.ifmo.ctddev.application.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import ru.ifmo.ctddev.application.R;

public class MafiaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private TextView mTextValue1;
    private TextView mTextValue2;
    private TextView mTextValue3;
    private TextView mTextValue4;

     SeekBar seekbar1;
    SeekBar seekbar2;
    SeekBar seekbar3;
    SeekBar seekbar4;

    public MafiaActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mafia);
         seekbar1 = (SeekBar)findViewById(R.id.seekBar1);
         seekbar2 = (SeekBar)findViewById(R.id.seekBar2);
         seekbar3 = (SeekBar)findViewById(R.id.seekBar3);
         seekbar4 = (SeekBar)findViewById(R.id.seekBar4);
        seekbar1.setOnSeekBarChangeListener(this);
        seekbar2.setOnSeekBarChangeListener(this);
        seekbar3.setOnSeekBarChangeListener(this);
        seekbar4.setOnSeekBarChangeListener(this);

        mTextValue1 = (TextView)findViewById(R.id.countPlayers1);
        mTextValue1.setText("0");

        mTextValue2 = (TextView)findViewById(R.id.countPlayers2);
        mTextValue2.setText("0");

        mTextValue3 = (TextView)findViewById(R.id.countPlayers3);
        mTextValue3.setText("0");

        mTextValue4 = (TextView)findViewById(R.id.countPlayers4);
        mTextValue4.setText("0");

    }

    public void back(View view) {
        this.finish();
    }
    public void showRules(View view) {
        Intent intent = new Intent(this, MafiaRulesActivity.class);
        startActivity(intent);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTextValue1.setText(String.valueOf(seekbar1.getProgress()));
        mTextValue2.setText(String.valueOf(seekbar2.getProgress()));
        mTextValue3.setText(String.valueOf(seekbar3.getProgress()));
        mTextValue4.setText(String.valueOf(seekbar4.getProgress()));
    }
    public void mafiaStartGame(View view) {
        Intent intent = new Intent(this, MafiaGameActivity.class);

        intent.putExtra("countPlayer1",seekbar1.getProgress());
        intent.putExtra("countPlayer2",seekbar2.getProgress());
        intent.putExtra("countPlayer3",seekbar3.getProgress());
        intent.putExtra("countPlayer4",seekbar4.getProgress());
        startActivity(intent);
    }
}
