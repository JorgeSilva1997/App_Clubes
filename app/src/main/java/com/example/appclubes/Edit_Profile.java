package com.example.appclubes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.USER.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Edit_Profile extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Number;
    private TextView Pass;
    private Button btnEdit;

    private String origem = "";
    private String name = "";
    private String email = "";
    private String number = "";
    private String pass = "";
    private String keyuser = "";

    private DatabaseReference myRef;
    private FirebaseAuth auth;

    private TextView textChangePass;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        textChangePass = (TextView)findViewById(R.id.textChangePass);
        final EditText editTextPass = new EditText(Edit_Profile.this);
        editTextPass.setHint("******");

        Name = (EditText)findViewById(R.id.Name);
        Email = (EditText)findViewById(R.id.Email);
        Number = (EditText)findViewById(R.id.Number);
        Pass = (TextView) findViewById(R.id.Pass);
        btnEdit = (Button)findViewById(R.id.btnedit);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        origem = bundle.getString("origem");

        if (origem.equals("editUser"))
        {
            number = bundle.getString("numberId");
            name = bundle.getString("name");
            pass = bundle.getString("password");
            email = bundle.getString("email");
            keyuser = bundle.getString("KeyUser");

            Name.setText(name.toString());
            Email.setText(email.toString());
            Number.setText(number.toString());
            Pass.setText(pass.toString());
        }

        // Criar Alerta para recuperar password
        textChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Profile.this);
                builder.setCancelable(false);

                builder.setTitle("Alteração de Password");
                builder.setMessage("Por favor insira uma nova password!");
                builder.setView(editTextPass);

                if (!editTextPass.getText().equals(""))
                {
                    builder.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            auth = FirebaseAuth.getInstance();

                            String emailRecuperar = editTextPass.getText().toString();

                            user.updatePassword(emailRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Log.d("Senha atualizada", "Senha atualizada com sucesso!");
                                        Toast.makeText(Edit_Profile.this, "Password alterada com sucesso!", Toast.LENGTH_SHORT).show();

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Edit_Profile.this, "Erro ao enviar e-mail!", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Edit_Profile.this, "Insira um e-mail!", Toast.LENGTH_SHORT).show();
                }

                alerta = builder.create();
                alerta.show();
            }
        });
    }

    private boolean UpdateUser(final User user)
    {
        btnEdit.setEnabled(false);

        try {
            myRef = ConfiguraçãoFirebase.getReference().child("users");
            //updatePass(user.getPassword());
            updateMail(user.getEmail());
            myRef.child(keyuser).setValue(user);

            Toast.makeText(Edit_Profile.this, "Conta atualizada com sucesso!", Toast.LENGTH_LONG).show();

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(Edit_Profile.this, "Erro: Conta não atualizada!", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    public void btnedit(View view)
    {
        String nome1 = Name.getText().toString();
        String email1 = Email.getText().toString();
        String number1 = Number.getText().toString();
        String pass1 = Pass.getText().toString();

        if (TextUtils.isEmpty(nome1)) {
            Name.setError("Please enter your name");
            Name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email1)) {
            Email.setError("Please enter your email");
            Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(number1)) {
            Number.setError("Please enter your number");
            Number.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass1)) {
            Pass.setError("Please enter your password");
            Pass.requestFocus();
            return;
        }
        else {

            User user = new User();
            user.setNumberId(Number.getText().toString());
            user.setName(Name.getText().toString());
            user.setPassword(Pass.getText().toString());
            user.setEmail(Email.getText().toString());
            user.setKeyUser(keyuser);

            UpdateUser(user);
        }

    }

    /*
    private void updatePass(String newPass)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(Edit_Profile.this, newPass, Toast.LENGTH_LONG).show();
        Toast.makeText(Edit_Profile.this, "" + user, Toast.LENGTH_LONG).show();

        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Log.d("Senha atualizada", "Senha atualizada com sucesso!");
                }
            }
        });
    }
    */

    private void updateMail(String newMail)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(Edit_Profile.this, newMail, Toast.LENGTH_LONG).show();
        Toast.makeText(Edit_Profile.this, "" + user, Toast.LENGTH_LONG).show();

        user.updateEmail(newMail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Log.d("Email atualizado", "Email atualizado com sucesso!");
                }
            }
        });
        finish();
    }
}
