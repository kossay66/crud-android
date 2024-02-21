package com.example.projectandriod;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class AddActivity extends AppCompatActivity {
    EditText nom, prenom, phone, cnum;
    Button add;
    Intent i;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        phone = findViewById(R.id.phone);
        cnum = findViewById(R.id.cnum);
        add = findViewById(R.id.add);
        requestQueue = Volley.newRequestQueue(this);
        add.setOnClickListener(v -> {
            addClient();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            i = new Intent(AddActivity.this, MainActivity.class);
            i.putExtra("phone", String.valueOf(phone.getText()));
            i.putExtra("nom", nom.getText());
            i.putExtra("prenom", phone.getText());
            i.putExtra("cnum", phone.getText());
            startActivity(i);
            //api
        });
    }


    private void addClient() {
        String url = "http://192.168.166.69:8080/client/" + Integer.parseInt(String.valueOf(phone.getText())) + "/" + nom.getText() + "/" + prenom.getText() + "/" + cnum.getText();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> Toast.makeText(AddActivity.this, response.toString(), Toast.LENGTH_SHORT).show(),
                error -> {
                    //Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    Log.e("api1", "error" + error.getLocalizedMessage());
                }
        );

        requestQueue.add(jsonObjectRequest);

    }
}