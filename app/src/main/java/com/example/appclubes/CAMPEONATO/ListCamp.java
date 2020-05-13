package com.example.appclubes.CAMPEONATO;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ATLETA.Atleta;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListCamp extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listacamp);

        lista = (ListView)findViewById(R.id.listCamp);
        Query query = FirebaseDatabase.getInstance().getReference().child("campeonato");
        FirebaseListOptions<Campeonato> options = new FirebaseListOptions.Builder<Campeonato>()
                .setLayout(R.layout.linha_camp)
                .setQuery(query,Campeonato.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoCamp = v.findViewById(R.id.NomedoCamp);

                Campeonato campeonato = (Campeonato) model;

                NomedoCamp.setText(campeonato.getNome());
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