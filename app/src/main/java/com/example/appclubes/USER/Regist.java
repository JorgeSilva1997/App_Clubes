package com.example.appclubes.USER;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Regist extends AppCompatActivity {

    EditText name, password, email, numberId;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);

        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        numberId = (EditText)findViewById(R.id.numberId);
    }

    public void btnregist(View view) {

        String nome = name.getText().toString();
        String pass = password.getText().toString();
        String mail = email.getText().toString();
        String number = numberId.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nome)) {
            name.setError("Please enter your username");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(number)) {
            numberId.setError("Please enter your number");
            numberId.requestFocus();
            return;
        }
        else {

            user = new User();

            user.setName(name.getText().toString());
            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());
            user.setNumberId(numberId.getText().toString());
            user.setTipo(0);


            registar();
        }
    }

    private void registar()
    {
        auth = ConfiguraçãoFirebase.getAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(Regist.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    registarUser(user);
                    finish();
                    auth.signOut();
                }
                else
                    {
                        String erro = "";

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e){
                            erro = "Senha fraca ou demasiado pequena. Combine letras e números no mínimo com 8 caracteres!";
                        } catch (FirebaseAuthInvalidCredentialsException e){
                            erro = "O email é inválido!";
                        } catch (FirebaseAuthUserCollisionException e){
                            erro = "Email já existente!";
                        } catch (Exception e) {
                            erro = "Erro ao registar!";
                            e.printStackTrace();
                        }

                        Toast.makeText(Regist.this, "Erro: " + erro, Toast.LENGTH_LONG).show();
                    }

            }
        });
    }

    private boolean registarUser(User user)
    {
        try {

            reference = ConfiguraçãoFirebase.getReference().child("users");
            // O push é equivalente à Primary_Key
            String key = reference.push().getKey();
            user.setKeyUser(key);
            reference.child(key).setValue(user);
            Toast.makeText(Regist.this, "Registado com sucesso!", Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(Regist.this, "Erro ao registar!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }
}
