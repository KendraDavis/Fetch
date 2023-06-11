package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static Boolean flag = false;
    static Boolean errorOccured = false;
    static int removed = 0;
    static ArrayList<DataModel> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        data(this);

        if(flag){
        data.sort(Comparator.comparing(DataModel::getListId).thenComparing(DataModel::getId));
        Data_view_adaptor adapter = new Data_view_adaptor(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        }
    }



    private static void data(Context context)  {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
                    URLConnection fetchData = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(fetchData.getInputStream()));

                    JSONObject job;

                    String inputLine = in.readLine();
                    while (inputLine != null) {
                        if(!inputLine.equals("[") && !inputLine.equals("]")) {
                            job = (JSONObject) new JSONTokener(inputLine).nextValue();
                            if(!job.get("name").equals(null) && !job.get("name").equals("")){
                                int listId = (int) job.get("listId");
                                String name = (String) job.get("name");
                                int id = (int) job.get("id");
                                data.add(new DataModel(name, listId, id));
                            } else {
                                removed++;
                            }

                        }
                        inputLine = in.readLine();
                    }

                    in.close();

                } catch (MalformedURLException e) {
                    Log.e("Error", "Malformed URL Exception");
                    errorOccured = true;
                } catch (IOException e) {
                    Log.e("Error", "IO Exception");
                    errorOccured = true;
                } catch (JSONException e) {
                    Log.e("Error", "JSON Exception");
                    errorOccured = true;
                } finally {
                    flag = true;

                }

            }

        });
        while (!flag) {
            // forces wait until thread finishes
        }

        if (errorOccured){
            AlertDialog error = (new AlertDialog.Builder(context).
                    setMessage("An error has occurred please close app")
                    .setCancelable(false)).create();
            error.show();
        }
    }
}