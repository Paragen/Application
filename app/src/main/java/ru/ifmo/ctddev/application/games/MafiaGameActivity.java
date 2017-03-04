package ru.ifmo.ctddev.application.games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ru.ifmo.ctddev.application.R;

public class MafiaGameActivity extends AppCompatActivity implements View.OnTouchListener {
    private TextView playersNumber;
    private ImageView playersImage;
    private static  int state;
    private int currentPlayer;
    private ArrayList<Player> players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mafia_game);
        state=1;
        currentPlayer=1;
        playersNumber = (TextView)findViewById(R.id.player);
        playersImage = (ImageView)findViewById(R.id.playersImage);

        View layout = (View) findViewById(R.id.mafiaGameLayout);
        layout.setOnTouchListener(this);

        int countPlayer1 = getIntent().getExtras().getInt("countPlayer1");
        int countPlayer2 = getIntent().getExtras().getInt("countPlayer2");
        int countPlayer3 = getIntent().getExtras().getInt("countPlayer3");
        int countPlayer4 = getIntent().getExtras().getInt("countPlayer4");
        System.out.println(countPlayer1+" "+countPlayer2+" "+countPlayer3+" "+countPlayer4);
         players = new ArrayList<>(countPlayer1);
        int countCitizen = countPlayer1 - countPlayer2 - countPlayer3 - countPlayer4;
        for(int i=1;i<=countCitizen;i++){
            players.add(Player.Citizen);
        }
        for(int i=1;i<=countPlayer2;i++){
            players.add(Player.Mafia);
        }
        for(int i=1;i<=countPlayer3;i++){
            players.add(Player.Whore);
        }
        for(int i=1;i<=countPlayer4;i++){
            players.add(Player.Commissar);
        }
        Collections.shuffle(players);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        state++;
        if (currentPlayer>players.size()){
            return false ;
        }
        if(state%2 !=0 ){
            currentPlayer++;
            playersNumber.setText(String.valueOf(currentPlayer));
            playersImage.setImageResource(R.drawable.card);


        }else{
            switch (players.get(currentPlayer-1)){
                case Citizen:
                    playersImage.setImageResource(R.drawable.citizen);
                    break;
                case Mafia:
                    playersImage.setImageResource(R.drawable.maf);
                    break;
                case Commissar:
                    playersImage.setImageResource(R.drawable.comm);
                    break;
                case Whore:
                    playersImage.setImageResource(R.drawable.whore);
                    break;
            }
        }
        return false;
    }
    public void back(View view) {
        this.finish();
    }
}
