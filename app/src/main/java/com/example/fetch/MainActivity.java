package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static Boolean flag = false; // indicates the data thread has successfully finished
    static Boolean errorOccurred = false; // if true an error has occurred
    static ArrayList<DataModel> data = new ArrayList<>();

    /**
     * Creates a recycler view to display the data, calls data(Context context) to fill the
     * ArrayList data. Then as long as the flag has been flipped to true indicating that data was
     * retrieved without any errors, it is sorted by listId value and then by id (the name is
     *"Item " + the id value. In the end a scrollable list of the information is presented to the
     * user
     */
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


    /**
     * The method creates a thread to connect to the internet and retrieve the data,
     * it then crates a new DataModel object for each value from the site that contains
     * a non-blank or null name. Finally, the DataModel objects are added to the ArrayList
     * data. If an error occurs during this process, the error message is logged and the
     * user receives a popup message saying that an error occurred, requiring them to restart
     * the app.
     *
     * @param context the context of the app when the method is called
     */
    private static void data(Context context)  {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

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
                        }
                    }
                    inputLine = in.readLine();
                }

                in.close();

            } catch (Exception e) {
                // I used the over arching Exception rather than specific because the error message
                // will display the details
                Log.e("Error", e.getMessage());
                errorOccurred = true;
            } finally {
                flag = true;
            }

        });

        while (!flag) {
            // forces wait until thread finishes
        }

        // if an error has been thrown display a message to the user to restart the app
        if (errorOccurred){
            AlertDialog error = (new AlertDialog.Builder(context).
                    setMessage("An error has occurred please restart app")
                    .setCancelable(false)).create();
            error.show();
        }
    }
}