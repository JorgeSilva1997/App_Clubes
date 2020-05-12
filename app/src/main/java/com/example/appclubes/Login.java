package com.example.appclubes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ADMIN.Main_admin;
import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.CONF.Preferences;
import com.example.appclubes.USER.Main;
import com.example.appclubes.USER.Regist;
import com.example.appclubes.USER.User;
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

    private TextView textForgotPass;
    private AlertDialog alerta;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

       textForgotPass = (TextView)findViewById(R.id.textForgotPass);

       final EditText editTextEmail = new EditText(Login.this);
       editTextEmail.setHint("exemplo@exemplo.com");

        myRef = FirebaseDatabase.getInstance().getReference();

        if (UserLog())
        {

               // String email = auth.getCurrentUser().getEmail().toString();
               // OpenMain(email);
               // finish();

        }
        else
            {
                Toast.makeText(Login.this, "Faça login!", Toast.LENGTH_SHORT).show();
            }

        // Criar Alerta para recuperar password
       textForgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
               builder.setCancelable(false);

               builder.setTitle("Recuperação de Password");
               builder.setMessage("Por favor insira o seu e-mail!");
               builder.setView(editTextEmail);

               if (!editTextEmail.getText().equals(""))
               {
                   builder.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           auth = FirebaseAuth.getInstance();

                           String emailRecuperar = editTextEmail.getText().toString();

                           auth.sendPasswordResetEmail(emailRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {

                                   if (task.isSuccessful()){
                                       Toast.makeText(Login.this, "Verifique o seu e-mail!", Toast.LENGTH_SHORT).show();

                                       Intent intent = getIntent();
                                       finish();
                                       startActivity(intent);
                                   }
                                   else{
                                       Toast.makeText(Login.this, "Erro ao enviar e-mail!", Toast.LENGTH_SHORT).show();

                                       Intent intent = getIntent();
                                       finish();
                                       startActivity(intent);
                                   }
                               }
                           });
                       }
                   });

                   builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent = getIntent();
                           finish();
                           startActivity(intent);
                       }
                   });
               }
               else
                   {
                       Toast.makeText(Login.this, "Insira um e-mail!", Toast.LENGTH_SHORT).show();
                   }

               alerta = builder.create();
               alerta.show();
           }
       });
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

                                Preferences preferences = new Preferences(Login.this);
                                preferences.GuardarCredencias(user.getEmail(), user.getPassword());
                                finish();

                                startActivity(intent);
                            } else if (tipoUserInt == 0){
                                Intent intent = new Intent(Login.this, Main.class);

                                Preferences preferences = new Preferences(Login.this);
                                preferences.GuardarCredencias(user.getEmail(), user.getPassword());
                                finish();

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
                    Toast.makeText(Login.this, "User ou senha inválida!", Toast.LENGTH_SHORT).show();
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

    private void OpenMain(String emailUser)
    {
        String email = auth.getCurrentUser().getEmail().toString();

        myRef.child("users").child("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String tipoUser = snapshot.child("tipo").getValue().toString();
                    int tipoUserInt1 = Integer.parseInt(tipoUser);

                    if (tipoUserInt1 == 1){
                        Intent intent = new Intent(Login.this, Main_admin.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (tipoUserInt1 == 0) {
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(Login.this, Main.class);
        startActivity(intent);
        finish();
    }

}
