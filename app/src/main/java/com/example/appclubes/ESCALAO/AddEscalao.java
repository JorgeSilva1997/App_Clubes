package com.example.appclubes.ESCALAO;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEscalao extends AppCompatActivity {

    EditText NomeEscalao;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Escalao escalao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addescalao);

        NomeEscalao = (EditText)findViewById(R.id.Escalao);
    }

    public void btnAddEscalao(View view)
    {
        String nameEscalao = NomeEscalao.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nameEscalao)) {
            NomeEscalao.setError("Por favor insira nome do campeonato!");
            NomeEscalao.requestFocus();
            return;
        }
        else {
            escalao = new Escalao();

            escalao.setNome(NomeEscalao.getText().toString());

            addEscalao();
        }
    }

    private void addEscalao()
    {
        AddEscalao(escalao);
    }

    private boolean AddEscalao(Escalao escalao)
    {
        try {

            reference = ConfiguraçãoFirebase.getReference().child("escalao");
            reference.push().setValue(escalao);
            Toast.makeText(AddEscalao.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e){
            Toast.makeText(AddEscalao.this, "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

}
