package com.example.appclubes.CONVOCATORIA;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddConvocatoria extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Convocatoria convocatoria;
    //// SPINNER's
    private Spinner spinnerEscalao, spinnerCampeonato;
    private Button btnAtletas, btnAddConvocatoria;
    private EditText LocalJogo, DateJogo, DateAntesJogo;
    // AUXILIARES
    private String xpto1, xpto3;
    ListView lista;
    ArrayList<String> arrayAtletas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addconvocatoria);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        LocalJogo = (EditText)findViewById(R.id.localJogo);
        DateJogo = (EditText)findViewById(R.id.dateJogo);
        DateAntesJogo = (EditText)findViewById(R.id.dateAntesJogo);

        preenchespinnerEscalao();
        preenchespinnerCampeonato();
    }
    // POP-UP
    public void btnAtletas(View view)
    {
        Intent intent = new Intent(AddConvocatoria.this, ObterAtletas.class);
        startActivity(intent);
    }
    private void preenchespinnerEscalao()
    {
        ObterEscaloes();

        spinnerEscalao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xpto = (String)parent.getItemAtPosition(position);
                xpto1 = xpto;
                Toast.makeText(AddConvocatoria.this, xpto + " selecionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ObterEscaloes()
    {

        spinnerEscalao = findViewById(R.id.spinnerEscalao);

        final ArrayList<String> escaloes = new ArrayList<>();

        reference.child("escalao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                escaloes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String NameEscalao = postSnapshot.child("nome").getValue().toString();
                    escaloes.add(NameEscalao);


                    ArrayAdapter dados = new ArrayAdapter(AddConvocatoria.this,  R.layout.support_simple_spinner_dropdown_item, escaloes);
                    dados.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEscalao.setAdapter(dados);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ///////////////////////////////////////////// FIM SPINNER ESCALAO //////////////////////////////////////////////////////////////////////////

    private void preenchespinnerCampeonato()
    {
        ObterCampeonatos();

        spinnerCampeonato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String xpto2 = (String)parent.getItemAtPosition(position);
                xpto3 = xpto2;
                Toast.makeText(AddConvocatoria.this, xpto2 + " selecionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ObterCampeonatos()
    {

        spinnerCampeonato = findViewById(R.id.spinnerCampeonato);

        final ArrayList<String> campeonatos = new ArrayList<>();

        reference.child("campeonato").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                campeonatos.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String NameCampeonato = postSnapshot.child("nome").getValue().toString();
                    campeonatos.add(NameCampeonato);


                    ArrayAdapter dados = new ArrayAdapter(AddConvocatoria.this,  R.layout.support_simple_spinner_dropdown_item, campeonatos);
                    dados.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerCampeonato.setAdapter(dados);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ///////////////////////////////////////////// FIM SPINNER CAMPEONATO //////////////////////////////////////////////////////////////////////////

    public void btnAddConvocatoria(View view)
    {
        String localJogo = LocalJogo.getText().toString();
        String dateJogo = DateJogo.getText().toString();
        String dateAntesJogo = DateAntesJogo.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(localJogo)) {
            LocalJogo.setError("Por favor insira um local!");
            LocalJogo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dateJogo)) {
            DateJogo.setError("Por favor insira uma data para o jogo!");
            DateJogo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dateAntesJogo)) {
            DateAntesJogo.setError("Por favor insira uma hora!");
            DateAntesJogo.requestFocus();
            return;
        }
        else {
            convocatoria = new Convocatoria();

            convocatoria.setEscalao(xpto1);
            convocatoria.setCampeonato(xpto3);
            // FALTA OS ATLETAS
            convocatoria.setLocal(LocalJogo.getText().toString());
            convocatoria.setHoraJogo(DateJogo.getText().toString());
            convocatoria.setHoraCedo(DateAntesJogo.getText().toString());

            addConvocatoria();
        }
    }

    private void addConvocatoria()
    {
        AddConvocatoria(convocatoria);
    }

    private boolean AddConvocatoria(Convocatoria convocatoria)
    {
        try {

            reference = ConfiguraçãoFirebase.getReference().child("convocatoria");
            reference.push().setValue(convocatoria);
            Toast.makeText(AddConvocatoria.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e){
            Toast.makeText(AddConvocatoria.this, "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

}
