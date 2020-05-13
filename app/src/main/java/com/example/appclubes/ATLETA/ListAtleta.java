package com.example.appclubes.ATLETA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ADMIN.Main_admin;
import com.example.appclubes.ADMIN.Regist_Admin;
import com.example.appclubes.CAMPEONATO.ChooseOptionCamp;
import com.example.appclubes.CAMPEONATO.ListCamp;
import com.example.appclubes.CONVOCATORIA.AddConvocatoria;
import com.example.appclubes.EQUIPA.AddEquipa;
import com.example.appclubes.ESCALAO.AddEscalao;
import com.example.appclubes.Info;
import com.example.appclubes.Perfil;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAtleta extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String TipoUserEmail;
    private TextView tipoUser;

    // Teste
    //// SPINNER's
    private Spinner spinnerEscalao;
    private String xpto1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listatleta);

        lista = (ListView)findViewById(R.id.listAtletas);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        Default();

        // Teste
        //preenchespinnerEscalao();
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


    public void Default()
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("atleta");
        FirebaseListOptions<Atleta> options = new FirebaseListOptions.Builder<Atleta>()
                .setLayout(R.layout.linha_atleta)
                .setQuery(query,Atleta.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoAtleta = v.findViewById(R.id.NomedoAtleta);
                TextView EscalaodoAtleta = v.findViewById(R.id.EscalaodoAtleta);

                Atleta atleta = (Atleta) model;

                NomedoAtleta.setText(atleta.getNome());
                EscalaodoAtleta.setText(atleta.getEscalao());
            }
        };

        lista.setAdapter(adapter);
    }

    public void OnlySub15(String xpto)
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("atleta").child("escalao").equalTo(xpto);
        FirebaseListOptions<Atleta> options = new FirebaseListOptions.Builder<Atleta>()
                .setLayout(R.layout.linha_atleta)
                .setQuery(query,Atleta.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoAtleta = v.findViewById(R.id.NomedoAtleta);
                TextView EscalaodoAtleta = v.findViewById(R.id.EscalaodoAtleta);

                Atleta atleta = (Atleta) model;

                NomedoAtleta.setText(atleta.getNome());
                EscalaodoAtleta.setText(atleta.getEscalao());
            }
        };

        lista.setAdapter(adapter);
    }



    // Teste par selecionar o escalão pretendido

    private void ObterEscaloes()
    {

        spinnerEscalao = findViewById(R.id.spinner2);

        final ArrayList<String> escaloes = new ArrayList<>();

        reference.child("escalao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                escaloes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String NameEscalao = postSnapshot.child("nome").getValue().toString();
                    escaloes.add(NameEscalao);


                    ArrayAdapter dados = new ArrayAdapter(ListAtleta.this,  R.layout.support_simple_spinner_dropdown_item, escaloes);
                    dados.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEscalao.setAdapter(dados);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void preenchespinnerEscalao()
    {
        ObterEscaloes();

        spinnerEscalao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xpto = (String)parent.getItemAtPosition(position);
                xpto1 = xpto;
                Toast.makeText(ListAtleta.this, xpto + " selecionado", Toast.LENGTH_SHORT).show();

                if (xpto1 == "Sub-15")
                {
                    OnlySub15(xpto);
                }
                else
                {
                    Default();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
