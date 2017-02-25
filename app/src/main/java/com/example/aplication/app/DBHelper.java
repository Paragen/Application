package com.example.aplication.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25.02.2017.
 */
class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table games ("
                + "id integer primary key,"
                + "name text,"
                + "version integer,"
                + "descr text,"
                + "shortdescr text" + ");");
    }

    public void setList(List<Game> games) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from games");

        db.beginTransaction();
        try {
            for (Game game : games) {
                ContentValues cv = new ContentValues();
                cv.put("name", game.getName());
                cv.put("id", game.getId());
                long rowID = db.insert("games", null, cv);

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
        this.close();
    }

    public void setGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descr", game.getDescr());
        cv.put("version", game.getVersion());
        String buff[] = new String[1];
        buff[0] = Integer.toString(game.getId());
        db.update("games", cv, "id=?", buff);
        db.close();
        this.close();
    }

    public List<Game> getList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Game> games = new ArrayList<>();
        Cursor c = db.query("games", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int versionColIndex = c.getColumnIndex("version");
            int descrColIndex = c.getColumnIndex("descr");
            int shortdescrColIndex = c.getColumnIndex("shortdescr");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Game game = new Game();
                game.setName(c.getString(nameColIndex));
                game.setId(c.getInt(idColIndex));
                game.setVersion(c.getInt(versionColIndex));
                game.setShortDescr(c.getString(shortdescrColIndex));
                game.setDescr(c.getString(descrColIndex));
                games.add(game);
//                Log.d(LOG_TAG,
//                        "ID = " + c.getInt(idColIndex) +
//                                ", name = " + c.getString(nameColIndex) +
//                                ", email = " + c.getString(emailColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else {
            return null;
        }
        c.close();
        db.close();
        this.close();

        return games;
    }

    public Game getGame(int id, int key) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query("games", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Game game;
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int versionColIndex = c.getColumnIndex("version");
            int descrColIndex = c.getColumnIndex("descr");
            int shortdescrColIndex = c.getColumnIndex("shortdescr");


            // получаем значения по номерам столбцов и пишем все в лог
            game = new Game();
            game.setName(c.getString(nameColIndex));
            game.setId(c.getInt(idColIndex));
            game.setVersion(c.getInt(versionColIndex));
            game.setShortDescr(c.getString(shortdescrColIndex));
            game.setDescr(c.getString(descrColIndex));

        } else {
            game = null;
        }
        db.close();
        this.close();
        return game;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}