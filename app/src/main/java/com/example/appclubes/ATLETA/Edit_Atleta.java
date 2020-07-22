package com.example.appclubes.ATLETA;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.Edit_Profile;
import com.example.appclubes.R;
import com.example.appclubes.USER.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Atleta extends AppCompatActivity {

    private Atleta atleta;

    private EditText Name;
    private EditText Escalao;
    private Button btnEdit;

    private String origem = "";
    private String KeyAtleta = "";
    private String nome, escalao;

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
            KeyAtleta = bundle.getString("KeyAtleta");

            //Toast.makeText(Edit_Atleta.this, "" + KeyAtleta, Toast.LENGTH_LONG).show();

            // Obter dados do Atleta
            FirebaseDatabase.getInstance().getReference().child("atleta").orderByChild("keyAtleta").equalTo(KeyAtleta).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        nome = postSnapshot.child("nome").getValue().toString();
                        Name.setText(nome);
                        //Toast.makeText(Edit_Atleta.this, "" + nome, Toast.LENGTH_LONG).show();

                        escalao = postSnapshot.child("escalao").getValue().toString();
                        Escalao.setText(escalao);
                        //Toast.makeText(Edit_Atleta.this, "" + escalao, Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
            Toast.makeText(Edit_Atleta.this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }   catch (Exception e){
            Toast.makeText(Edit_Atleta.this, "Erro ao atualizar!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private void updateAtleta()
    {
        UpdateAtleta(atleta);
    }
}
