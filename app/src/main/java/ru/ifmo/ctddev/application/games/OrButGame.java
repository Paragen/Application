package ru.ifmo.ctddev.application.games;

import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.ifmo.ctddev.application.R;
import ru.ifmo.ctddev.application.database.Game;


public class OrButGame extends AbstractGameActivity implements View.OnClickListener {
    static final private String TAG = "Or But";

    private List<String> wordsList;
    private int shift = 0;

    @Override
    protected void welcome() {
        setContentView(R.layout.orbut_welcome);
        ((Button) findViewById(R.id.orbut_welcome_button)).setClickable(false);
    }

    @Override
    protected void updateView(Game game) {
        if (wordsList == null) {
            wordsList = new ArrayList<>();
            // todo remake
            try (JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(game.getDescr().getBytes()), "UTF-8"))) {
                reader.beginArray();

                while (reader.hasNext()) {
                    wordsList.add(reader.nextString());
                }

            } catch (IOException e) {

            }

            ((Button) findViewById(R.id.orbut_welcome_button)).setClickable(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.orbut_button_back:
                finishActivity(0);
                break;
            case R.id.orbut_button_rules:
                setContentView(R.layout.choose_rules);
                break;
            case R.id.orbut_welcome_button:
                setContentView(R.layout.or_but);
            default:
                nextSlide();

        }
    }

    private void nextSlide() {
        String str = wordsList.get(shift++);
        if (shift == wordsList.size()) {
            Collections.shuffle(wordsList);
        }
        String buf[] = str.split("|");

        if (buf.length == 2) {
            ((TextView) findViewById(R.id.orbut_high_textView)).setText(buf[0]);
            ((TextView) findViewById(R.id.orbut_low_textView)).setText(buf[1]);
        } else {
            Log.d(TAG, "Incorrect data");
        }
    }
}
