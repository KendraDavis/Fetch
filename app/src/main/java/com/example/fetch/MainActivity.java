package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.AsynchronousChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView.findViewById(R.id.textView);
        String data =  data();
    }

    private static String data(){
            Executor executor = Executors.newSingleThreadExecutor();
            String vals = "";
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
                        Log.d("loger", inputLine);
                         inputLine = in.readLine();
                        Log.d("loger", inputLine);

                        in.close();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//            InputStream in = url.openStream();
//            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

                }
            });

        return "NULL";
    }
}