package com.example.appclubes.ATLETA;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.ESCALAO.AddEscalao;
import com.example.appclubes.ESCALAO.Escalao;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAtleta extends AppCompatActivity {

    EditText NomeAtleta;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Atleta atleta;
    private Spinner spinner;
    private Escalao escalao;
    private String xpto1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addatleta);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        NomeAtleta = (EditText)findViewById(R.id.NameAtleta);

        preenchespinner();
    }

    private void ObterEscaloes()
    {

        spinner = findViewById(R.id.spinner);

        final ArrayList<String> escaloes = new ArrayList<>();

        reference.child("escalao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                escaloes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String NameEscalao = postSnapshot.child("nome").getValue().toString();
                    escaloes.add(NameEscalao);


                    ArrayAdapter dados = new ArrayAdapter(AddAtleta.this, android.R.layout.simple_spinner_dropdown_item, escaloes);
                    dados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dados);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void btnAddAtleta(View view)
    {
        String nameAtleta = NomeAtleta.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nameAtleta)) {
            NomeAtleta.setError("Por favor insira o nome do atleta!");
            NomeAtleta.requestFocus();
            return;
        }
        else {
            atleta = new Atleta();

            atleta.setNome(NomeAtleta.getText().toString());
            atleta.setEscalao(xpto1);

            addAtleta();
        }
    }


    private void addAtleta()
    {
        AddAtleta(atleta);
    }

    private boolean AddAtleta(Atleta atleta)
    {
        try {

            reference = ConfiguraçãoFirebase.getReference().child("atleta");
            String key = reference.push().getKey();
            atleta.setKeyAtleta(key);
            reference.child(key).setValue(atleta);
            Toast.makeText(AddAtleta.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();

/*
            String key1 = ConfiguraçãoFirebase.getReference().child("atleta").push().getKey();
            Atleta atleta1 = new Atleta(keyAtleta, nome, escalao);
            Map<String, Object> postValues = atleta1.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/atleta/" + key1, postValues);
            childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
*/

            return true;
        }   catch (Exception e){
            Toast.makeText(AddAtleta.this, "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private void preenchespinner()
    {
        ObterEscaloes();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xpto = (String)parent.getItemAtPosition(position);
                xpto1 = xpto;
                Toast.makeText(AddAtleta.this, xpto + " selecionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
