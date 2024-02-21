package com.example.projectandriod;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://192.168.166.69:8080/client";
    public Adapter adapter;
    FloatingActionButton fab;
    FloatingActionButton fabmap;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<client> clientList;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        fabmap = findViewById(R.id.fabMap);
        swipeRefreshLayout = findViewById(R.id.swipe);
        clientList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        //setClientInfo();
        getAllClients();
        adapter = new Adapter(clientList, this);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setAdapter();
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
            adapter.notifyDataSetChanged();
        });
        fabmap.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {

            Intent i = getIntent();
            Integer phonedel = i.getIntExtra("phone", 0);
            for (client client : clientList) {
                if (client.getPhone() == phonedel) {
                    clientList.remove(client);
                }
            }
            //getAllClients();
            adapter.setList(clientList);

            swipeRefreshLayout.setRefreshing(false);
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
       // getAllClients();
        adapter.notifyDataSetChanged();
        //fun1();
    }


    private void setAdapter() {
        //Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void getAllClients() {

        String url = BASE_URL;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.i("TAG", "Response is: " + response.toString());
            try {
                // Parse the JSONArray and process the data
                for (int i = 0; i < response.length(); i++) {
                    JSONObject client = response.getJSONObject(i);
                    // Extract relevant data and do something with it
                    String name = client.getString("nom");
                    String surname = client.getString("prenom");
                    Integer phone = client.getInt("phone");
                    String cnum = client.getString("cnum");
                    client clientTemp = new client(phone, name, surname, cnum);
                    clientList.add(clientTemp);
                    // Perform operations with the data (e.g., display in UI)
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            Log.e("api1", "error" + error.getLocalizedMessage());
            if (error.networkResponse != null) {
                int statusCode = error.networkResponse.statusCode;
                Log.e("api2", "HTTP Status Code: " + statusCode);
            }
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            Log.e("api3", "error" + error.networkResponse);
        });

        requestQueue.add(jsonArrayRequest);

    }

}