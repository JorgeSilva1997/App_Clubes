package com.example.appclubes.CAMPEONATO;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.ESCALAO.Escalao;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCampeonato extends AppCompatActivity {

    EditText NomeCampeonato;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Campeonato campeonato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcampeonato);

        NomeCampeonato = (EditText)findViewById(R.id.Campeonato);
    }

    public void btnAddCampeonato(View view)
    {
        String NameCampeonato = NomeCampeonato.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(NameCampeonato)) {
            NomeCampeonato.setError("Por favor insira nome do campeonato!");
            NomeCampeonato.requestFocus();
            return;
        }
        else {
            campeonato = new Campeonato();

            campeonato.setNome(NomeCampeonato.getText().toString());

            addCampeonato();
        }
    }

    public void addCampeonato() {AddCampeonato(campeonato);}

    private boolean AddCampeonato(Campeonato campeonato)
    {
        try {

            reference = ConfiguraçãoFirebase.getReference().child("campeonato");
            String key = reference.push().getKey();
            campeonato.setKeyCamp(key);
            reference.child(key).setValue(campeonato);
            Toast.makeText(AddCampeonato.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e) {
            Toast.makeText(AddCampeonato.this,  "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }
}
