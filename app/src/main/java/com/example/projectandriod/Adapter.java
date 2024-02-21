package com.example.projectandriod;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    private ArrayList<client> clientList;

    public Adapter(ArrayList<client> clientList, Context context) {

        this.clientList = clientList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        String nom = clientList.get(position).getNom();
        String prenom = clientList.get(position).getPrenom();
        Integer phone = clientList.get(position).getPhone();
        String cnum = clientList.get(position).getCode();
        holder.nom.setText(nom);
        holder.phone.setText(String.valueOf(phone));
        holder.cnum.setText(cnum);
        holder.prenom.setText(prenom);
    }

    public void setList(ArrayList<client> liste){
        clientList.clear();
        clientList=liste;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nom, phone, cnum, prenom;
        Button update, delete;
        private RequestQueue requestQueue;

        public MyViewHolder(final View view) {
            super(view);
            nom = view.findViewById(R.id.nom);
            prenom = view.findViewById(R.id.prenom);

            phone = view.findViewById(R.id.phone);
            cnum = view.findViewById(R.id.cnum);
            update = view.findViewById(R.id.update);
            delete = view.findViewById(R.id.delete);
            requestQueue = Volley.newRequestQueue(context);


            update.setOnClickListener(v -> {
                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("nom", nom.getText());
                i.putExtra("prenom", prenom.getText());
                i.putExtra("phone", phone.getText());
                i.putExtra("cnum", cnum.getText());
                context.startActivity(i);

            });
            delete.setOnClickListener(v -> {
                //API
                String url = "http://192.168.166.69:8080/client/" + Integer.parseInt(phone.getText().toString());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                        response -> {
                            Log.e("response", response.toString());
                            //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        },
                        error -> {
                            Log.e("error", error.getLocalizedMessage());
                            //Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                );

                requestQueue.add(jsonObjectRequest);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("phone", Integer.parseInt((String) phone.getText()));
                context.startActivity(i);


            });
        }
    }
}
