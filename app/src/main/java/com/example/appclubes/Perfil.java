package com.example.appclubes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CONF.ConfiguraçãoFirebase;
import com.example.appclubes.USER.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private String origem = "";
    private String name1 = "";
    private String email1 = "";
    private String number1 = "";
    private String pass1 = "";
    private String keyuser = "";

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

    public void btnRemoveProfile(View view)
    {
        String emailUserLogado = auth.getCurrentUser().getEmail();

        myRef.child("users").orderByChild("email").equalTo(emailUserLogado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    final User user = snapshot.getValue(User.class);

                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

                    user1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Log.d("USER REMOVIDO", "User removido");

                                Toast.makeText(Perfil.this, "Conta removida com sucesso!", Toast.LENGTH_LONG).show();

                                myRef = ConfiguraçãoFirebase.getReference();
                                myRef.child("users").child(user.getKeyUser()).removeValue();

                                auth.signOut();
                                OpenLogin();
                            }
                            else
                                {

                                }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnEditProfile(View view)
    {
        String emailUserLog = auth.getCurrentUser().getEmail();
        myRef = ConfiguraçãoFirebase.getReference();


        myRef.child("users").orderByChild("email").equalTo(emailUserLog).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);

                    final Intent intent = new Intent(Perfil.this, Edit_Profile.class);
                    final Bundle bundle = new Bundle();

                    bundle.putString("origem", "editUser");

                    bundle.putString("numberId", user.getNumberId());
                    bundle.putString("name", user.getName());
                    bundle.putString("password", user.getPassword());
                    bundle.putString("email", user.getEmail());
                    bundle.putString("KeyUser", user.getKeyUser());

                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void OpenLogin()
    {
        Intent intent = new Intent(Perfil.this, Login.class);
        startActivity(intent);
        finish();
    }

}
