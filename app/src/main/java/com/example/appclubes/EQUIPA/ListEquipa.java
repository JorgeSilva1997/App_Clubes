package com.example.appclubes.EQUIPA;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CAMPEONATO.Campeonato;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListEquipa extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaequipa);

        lista = (ListView)findViewById(R.id.listEquipa);
        Query query = FirebaseDatabase.getInstance().getReference().child("equipa");
        FirebaseListOptions<Equipa> options = new FirebaseListOptions.Builder<Equipa>()
                .setLayout(R.layout.linha_equipa)
                .setQuery(query,Equipa.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedaEquipa = v.findViewById(R.id.NomedaEquipa);

                Equipa equipa = (Equipa) model;

                NomedaEquipa.setText(equipa.getNome());
            }
        };

        lista.setAdapter(adapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}