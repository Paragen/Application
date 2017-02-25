package com.example.aplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by k-par_000 on 23.02.2017.
 */
public class GameListActivity extends Activity implements DownloadCallback{

    private final static String TAG = "GameList";

    private List<String> list;
    private AsyncLoader loader;
    private SharedPreferences preferences;
    private Map<Integer, Game> itemMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        preferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        String listVersion = preferences.getString(getString(R.string.list_version), "0");

        loader = new AsyncLoader(this);
        loader.execute("command=games&version=" + listVersion);

    }

    void loadListFromDB() {
        setProperty(new DBHelper(this).getList());
        Log.d(TAG, "List loaded from DB");
    }


    // called after AsyncLoader end loading
    @Override
    public void onLoadComplete(String data) {

        List<Game> list = new ArrayList<>();

        try  {
            JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));

            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "status":
                        if (reader.nextString().toLowerCase().equals("ok")) {
                            loadListFromDB();
                            return;
                        }
                        break;
                    case "version":
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(getString(R.string.list_version), reader.nextString());
                        editor.commit();
                        break;
                    case "games":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();

                            //todo fix string build
                            Game el = new Game();
                            while (reader.hasNext()) {

                                switch (reader.nextName()) {
                                    case "name":
                                        el.setName(reader.nextString());
                                        break;
                                    case "id":
                                        el.setId(reader.nextInt());
                                        break;
                                }

                            }
                            list.add(el);
                            reader.endObject();
                        }

                        reader.endArray();
                        break;
                }
            }

            Log.d(TAG,"Json parsing completed");
        } catch (IOException e) {
            Log.d(TAG,"Json parsing failed:" + e.getMessage());
        }

        setProperty(list);
        Log.d(TAG, "List loaded from server");

        new DBHelper(this).setList(list);
        Log.d(TAG, "Data base updated");

    }

    //fill list
    void setProperty(List<Game> list) {
        ListView view = (ListView) findViewById(R.id.listView);

        String buf[] = new String[0];
        Game game;
        Iterator<Game> iter = list.iterator();
        List<String> sList = new ArrayList<>();

        while (iter.hasNext()) {
            game = iter.next();
            sList.add(game.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_item_layout, sList.toArray(buf));

        view.setAdapter(adapter);

        itemMap = new HashMap<>();

        iter = list.iterator();
        int i = 0;

        while (iter.hasNext()) {
            itemMap.put(++i, iter.next());
        }
    }

    // item onClick listener
    public void onItemClick(View view) {
        int num = ((ListView)findViewById(R.id.listView)).getPositionForView(view);
        Game game = itemMap.get(num + 1);
        Intent intent = new Intent(this, gameActivityNum1.class);
        intent.putExtra(gameActivityNum1.ARG_STR, game.getId());
        Log.d(TAG, "Start game with id: " + Integer.toString(game.getId()));
        startActivity(intent);
    }

    void reset() {
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.list_version), "0");
            editor.commit();
        }
    }

    public void onResetButtonClick(View view) {
        reset();
    }
}
