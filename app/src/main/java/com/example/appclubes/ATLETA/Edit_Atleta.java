package com.example.appclubes.ATLETA;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.Edit_Profile;
import com.example.appclubes.R;
import com.example.appclubes.USER.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_Atleta extends AppCompatActivity {

    private Atleta atleta;

    private EditText Name;
    private EditText Escalao;
    private Button btnEdit;

    private String origem = "";
    private String nome = "";
    private String escalao = "";
    private String KeyAtleta = "";

    private DatabaseReference myRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_atleta);

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Name = (EditText)findViewById(R.id.Name);
        Escalao = (EditText)findViewById(R.id.Escalao);
        btnEdit = (Button)findViewById(R.id.btnedit);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        origem = bundle.getString("origem");

        if (origem.equals("editAtleta"))
        {
            nome = bundle.getString("nome");
            escalao = bundle.getString("escalao");
            KeyAtleta = bundle.getString("KeyAtleta");

            Name.setText(nome);
            Escalao.setText(escalao);
        }

    }


    public void btnedit(View view)
    {
        String nome1 = Name.getText().toString();
        String email1 = Escalao.getText().toString();

        if (TextUtils.isEmpty(nome1)) {
            Name.setError("Please enter your name");
            Name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email1)) {
            Escalao.setError("Please enter your email");
            Escalao.requestFocus();
            return;
        }
        else {

            Atleta atleta = new Atleta();
            atleta.setNome(Name.getText().toString());
            atleta.setEscalao(Escalao.getText().toString());
            atleta.setKeyAtleta(KeyAtleta);

            updateAtleta();
        }
    }

    private boolean UpdateAtleta(Atleta atleta)
    {
        btnEdit.setEnabled(false);

        try {

            myRef = ConfiguraçãoFirebase.getReference().child("atleta");
            myRef.child(KeyAtleta).setValue(atleta);
            Toast.makeText(Edit_Atleta.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e){
            Toast.makeText(Edit_Atleta.this, "Erro ao inserir!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private void updateAtleta()
    {
        UpdateAtleta(atleta);
    }
}
