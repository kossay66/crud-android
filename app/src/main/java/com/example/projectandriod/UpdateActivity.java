package com.example.projectandriod;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class UpdateActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://your-api-base-url";
    EditText nom, prenom, cnum;
    TextView phone;
    Button update;
    Intent intent;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        phone = findViewById(R.id.phone);
        cnum = findViewById(R.id.cnum);
        update = findViewById(R.id.update);
        requestQueue = Volley.newRequestQueue(this);
        intent = getIntent();
        nom.setText(String.valueOf(intent.getStringExtra("nom")));
        prenom.setText(String.valueOf(intent.getStringExtra("prenom")));
        phone.setText(String.valueOf(intent.getStringExtra("phone")));
        cnum.setText(String.valueOf(intent.getStringExtra("cnum")));


        update.setOnClickListener(v -> {
            //api
            String url = "http://192.168.166.69:8080/client/" + Integer.parseInt(String.valueOf(phone.getText())) + "/" + nom.getText() + "/" + prenom.getText() + "/" + cnum.getText();


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                    response -> Toast.makeText(UpdateActivity.this, response.toString(), Toast.LENGTH_SHORT).show(),
                    error -> {
                        //Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("api1", "error" + error.getLocalizedMessage());
                    }
            );

            requestQueue.add(jsonObjectRequest);
            Intent i = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(i);
        });

    }

}