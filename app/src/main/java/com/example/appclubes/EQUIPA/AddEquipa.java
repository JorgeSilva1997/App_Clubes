package com.example.appclubes.EQUIPA;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CAMPEONATO.AddCampeonato;
import com.example.appclubes.CAMPEONATO.Campeonato;
import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEquipa extends AppCompatActivity {

    EditText NomeEquipa;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Equipa equipa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addequipa);

        NomeEquipa = (EditText)findViewById(R.id.Equipa);
    }

    public void btnAddEquipa(View view)
    {
        String NameEquipa = NomeEquipa.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(NameEquipa)) {
            NomeEquipa.setError("Por favor insira nome da equipa!");
            NomeEquipa.requestFocus();
            return;
        }
        else {
            equipa = new Equipa();

            equipa.setNome(NomeEquipa.getText().toString());

            addEquipa();
        }
    }

    public void addEquipa() {AddEquipa(equipa);}

    private boolean AddEquipa(Equipa equipa)
    {
        try {


            reference = ConfiguraçãoFirebase.getReference().child("equipa");
            String key = reference.push().getKey();
            equipa.setKeyAtleta(key);
            reference.child(key).setValue(equipa);
            Toast.makeText(AddEquipa.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e){
            Toast.makeText(AddEquipa.this,  "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }
}
