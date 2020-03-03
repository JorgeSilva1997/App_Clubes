package com.example.appclubes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Perfil extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseAuth auth;
    TextView name, mail, numberId, pass;
    private String nome, mails, number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        auth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        name = (TextView) findViewById(R.id.name);
        mail = (TextView) findViewById(R.id.mail);
        numberId = (TextView) findViewById(R.id.numberId);
        pass = (TextView) findViewById(R.id.pass);



        // Isto serve para receber o email do user logado no momento
        String email = auth.getCurrentUser().getEmail().toString();
        myRef.child("users").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    nome = postSnapshot.child("name").getValue().toString();
                    name.setText(nome);

                    mails = postSnapshot.child("email").getValue().toString();
                    mail.setText(mails);

                    number = postSnapshot.child("numberId").getValue().toString();
                    numberId.setText(number);

                    password = postSnapshot.child("password").getValue().toString();
                    pass.setText(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
