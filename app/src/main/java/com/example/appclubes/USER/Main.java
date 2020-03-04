package com.example.appclubes.USER;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.Info;
import com.example.appclubes.Login;
import com.example.appclubes.Perfil;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Main extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private User user;
    private String TipoUserEmail;
    private TextView tipoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        auth = FirebaseAuth.getInstance();
        tipoUser = (TextView)findViewById(R.id.txtTipoUser);
        reference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //////////////////////////////////////////////////////


        // Isto serve para receber o email do user logado no momento
        String email = auth.getCurrentUser().getEmail().toString();
        reference.child("users").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    TipoUserEmail = postSnapshot.child("name").getValue().toString();
                    tipoUser.setText(TipoUserEmail);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////////////////////

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout)
        {
            Logout();
        }
        else if (id == R.id.infoProg)
        {
            Intent intent = new Intent(Main.this, Info.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void Logout()
    {
        auth.signOut();

        Intent intent = new Intent(Main.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void btnPerfil(View view)
    {
        Intent intent = new Intent(Main.this, Perfil.class);
        startActivity(intent);
    }
}
