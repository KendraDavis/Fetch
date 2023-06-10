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
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
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
    static JSONArray arr1 = new JSONArray();
    static JSONArray arr2 = new JSONArray();
    static JSONArray arr3 = new JSONArray();
    static JSONArray arr4 = new JSONArray();

    static JSONArray[] sorted = new JSONArray[4];

    static int removed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("points", "one");
        textView = findViewById(R.id.textView);
        sorted[0] = arr1;
        sorted[1] = arr2;
        sorted[2] = arr3;
        sorted[3] = arr4;

        data();
        int i = 0;
        while (!flag) {
            i++;
        }
        dataParse();
        Log.d("d", String.valueOf(removed));
        if(errorOccured){
            String errMess = "An error has occurred, please start the app";
            textView.setText(errMess);
        } else {
            textView.setText(dataStream);
        }


    }

    private void dataParse() {
        int counter = 0;
for(int j = 0; j < sorted.length; j++){
        for(int i = 0; i < sorted[j].length(); i++) {
            try {
                JSONObject obj = sorted[j].getJSONObject(i);
                counter++;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        }
        Log.d("counter", String.valueOf(counter));


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

                    JSONObject job;

                    String inputLine = in.readLine();
                    while (inputLine != null) {
                        if(!inputLine.equals("[") && !inputLine.equals("]")) {
                            dataStream += '\n' + inputLine;
                            job = (JSONObject) new JSONTokener(inputLine).nextValue();
                            int id = (int) job.get("listId");
                            if(!job.get("name").equals(null) && !job.get("name").equals("")){
                                sorted[id - 1].put(job);
                            } else {
                                removed++;
                            }

                        } else {
                            Log.d("symbol", inputLine);
                        }
                        inputLine = in.readLine();
                    }

                } catch (MalformedURLException e) {
                    Log.e("Error", "Malformed URL Exception");
                    errorOccured = true;
                } catch (IOException e) {
                    Log.e("Error", "IO Exception");
                    errorOccured = true;

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                flag = true;

            }

        });
    }
}