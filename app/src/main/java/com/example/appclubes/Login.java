package com.example.appclubes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    EditText mail, pass;
    private DatabaseReference myRef;
    private FirebaseAuth auth;
    private User user;
    private String tipoUser;
    private int tipoUserInt;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        myRef = FirebaseDatabase.getInstance().getReference();

        if (UserLog())
        {
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
        }
        else
            {
                Toast.makeText(Login.this, "Faça login!", Toast.LENGTH_SHORT).show();
            }

    }

    public void btnRegist(View view){

        Intent intent = new Intent(Login.this, Regist.class);
        startActivity(intent);
    }

    public void btnLogin(View view) {

        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);

        String email = mail.getText().toString();
        String password = pass.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            mail.setError("Please enter your email");
            mail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            pass.setError("Please enter your password");
            pass.requestFocus();
            return;
        }
        else {

            autenticacao(email, password);
        }
    }

    public void autenticacao(final String email, String password)
    {
        user = new User();

        user.setEmail(mail.getText().toString());
        user.setPassword(pass.getText().toString());

        auth = ConfiguraçãoFirebase.getAuth();
        auth.signInWithEmailAndPassword(user.getEmail().toString(), user.getPassword().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {

                    myRef.child("users").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                            {
                                tipoUser = postSnapshot.child("tipo").getValue().toString();
                                tipoUserInt = Integer.parseInt(tipoUser);


                            if (tipoUserInt == 1) {
                                Intent intent = new Intent(Login.this, Main_admin.class);
                                startActivity(intent);
                            } else if (tipoUserInt == 0){
                                Intent intent = new Intent(Login.this, Main.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(Login.this, "Erro!" + tipoUser, Toast.LENGTH_LONG).show();
                            }
                        }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
                else {
                    Toast.makeText(Login.this, "User ou senaha inválida!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Boolean UserLog()
    {
        FirebaseUser utilizador = FirebaseAuth.getInstance().getCurrentUser();

        if (utilizador != null)
        {
            return true;
        }
        else
            {
                return false;
            }
    }

}
