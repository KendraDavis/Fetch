package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.AsynchronousChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    static Boolean flag = false;
    static Boolean errorOccured = false;
    static String dataStream = "";
    static JSONArray arr = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("points", "one");
        textView = findViewById(R.id.textView);
//        try {
            data();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        int i = 0;
        while (!flag) {
            i++;
        }
        Log.d("points", String.valueOf(i));

        if(errorOccured){
            String errMess = "An error has occurred, please start the app";
            textView.setText(errMess);
        } else {
            textView.setText(dataStream);
        }


    }

    private static void data()  {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                URL url = null;
                BufferedReader in;
                try {

                    url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
                    URLConnection fetchData = url.openConnection();
                    in = new BufferedReader(new InputStreamReader(fetchData.getInputStream()));



                    String inputLine = in.readLine();
                    while (inputLine != null) {
                        dataStream += '\n' + inputLine;
                        inputLine = in.readLine();
                    }

                } catch (MalformedURLException e) {
                    Log.e("Error", "Malformed URL Exception");
                    errorOccured = true;
                } catch (IOException e) {
                    Log.e("Error", "IO Exception");
                    errorOccured = true;

                }
                flag = true;

            }

        });
    }
}