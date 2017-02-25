package ru.ifmo.ctddev.application.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by k-par_000 on 24.02.2017.
 */
public class AsyncLoader extends AsyncTask<String, Void, String> {

    static final String QUERY = "http://pizdyk.000webhostapp.com/";
    private static final String TAG = "AsyncLoader";

    private DownloadCallback callback;

    public AsyncLoader(DownloadCallback callback) {
        setCallback(callback);
    }

    public void setCallback(DownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String answer = "";
        if (callback != null && strings != null) {

            String message = strings[0];
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(QUERY).openConnection();
                conn.setConnectTimeout(5000);
//              conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                OutputStream os = conn.getOutputStream();
                os.write(message.getBytes("UTF-8"));
                os.close();

                InputStreamReader is = new InputStreamReader(conn.getInputStream(), "UTF-8");
                final int mSize = 1024 * 1024 * 10;
                int size = 0, curr = 0;
                char buf[] = new char[mSize];
                while (size < mSize) {
                    if ((curr = is.read(buf, size, mSize - size)) < 0) {
                        break;
                    }
                    size += curr;
                }
                answer = new String(buf, 0, size);
            } catch (Exception e) {
                Log.d(TAG, "Loading failed");
            }

            Log.d(TAG, "Loading completed");

        }
        return answer;
    }

    @Override
    protected void onPostExecute(String answer) {
        callback.onLoadComplete(answer);
    }
}
