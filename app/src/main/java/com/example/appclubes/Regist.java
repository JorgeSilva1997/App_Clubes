package com.example.appclubes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Regist extends AppCompatActivity {

    EditText name, password, email, numberId;
    private User user;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);

        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        numberId = (EditText)findViewById(R.id.numberId);

        user = new User();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
    }

    public void btnregist(View view) {

        //database = FirebaseDatabase.getInstance();
        //reference = database.getReference().child("message");

        //String nome = name.getText().toString();
        //String pass = password.getText().toString();
        //String mail = email.getText().toString();
        //String number = numberId.getText().toString();
        //String nif = NIF.getText().toString();

        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.setNumberId(numberId.getText().toString());
        user.setTipo("normal");

        myRef.child(user.getNumberId()).setValue(user);
        //reference.setValue(nome);
        //reference.setValue(pass);
        //reference.setValue(mail);
        //reference.setValue(number);
        //reference.setValue("normal");
    }

    /*public void autenticacao_firebase(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())    {
                    Toast.makeText(Regist.this, "Registado com sucesso", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Regist.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

}
