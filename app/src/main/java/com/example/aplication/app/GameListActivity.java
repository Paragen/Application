package com.example.aplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by k-par_000 on 23.02.2017.
 */
public class GameListActivity extends Activity implements DownloadCallback{

    private final static String TAG = "GameList";

    List<String> list;
    AsyncLoader loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        loader = new AsyncLoader(this);
        loader.execute("command=games&version=0");

    }

//    @Override
//    public  void onStart() {
//        super.onStart();
//
//    }

    //todo make function definition
    List<String> loadListFromDB() {
        return Collections.EMPTY_LIST;
    }

    void onClick(View view) {
        Intent intent = new Intent(this, gameActivityNum1.class);
        startActivity(intent);
    }

    @Override
    public void onLoadComplete(String data) {

        List<String> list = loadListFromDB();

        try  {
            JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));

            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "status":
                        if (reader.nextString().toLowerCase().equals("ok")) {
                            //todo  перенести парсинг в отдельную функцию
                            return;
                        }
                        break;
                    case "version":
                        //todo надо бы записать
                        reader.skipValue();
                        break;
                    case "games":
                        list = new ArrayList<String>();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();

                            //todo fix string build
                            while (reader.hasNext()) {
                                String s = "";
                                switch (reader.nextName()) {
                                    case "name":
                                        s += reader.nextString();
                                        break;
                                    case "id":
                                        s += reader.nextString();
                                        break;
                                }
                                list.add(s);
                            }

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

        ListView view = (ListView) findViewById(R.id.listView);

        String buf[] = new String[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_item_layout, list.toArray(buf));

        view.setAdapter(adapter);
    }

    public void onItemClick(View view) {
        int num = ((ListView)findViewById(R.id.listView)).getPositionForView(view);
        Intent intent = new Intent(this, gameActivityNum1.class);
        intent.putExtra(gameActivityNum1.ARG_STR,"command=game&id="+Integer.toString(num)+"&version=0");
        startActivity(intent);
    }
}
