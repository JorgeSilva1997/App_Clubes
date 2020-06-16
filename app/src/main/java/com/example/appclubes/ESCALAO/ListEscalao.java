package com.example.appclubes.ESCALAO;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CAMPEONATO.Campeonato;
import com.example.appclubes.EQUIPA.Equipa;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListEscalao extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaescalao);

        lista = (ListView)findViewById(R.id.listEscalao);
        Query query = FirebaseDatabase.getInstance().getReference().child("escalao");
        FirebaseListOptions<Escalao> options = new FirebaseListOptions.Builder<Escalao>()
                .setLayout(R.layout.linha_escalao)
                .setQuery(query,Escalao.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoEscalao = v.findViewById(R.id.NomedoEscalao);

                Escalao escalao = (Escalao) model;

                NomedoEscalao.setText(escalao.getNome());
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