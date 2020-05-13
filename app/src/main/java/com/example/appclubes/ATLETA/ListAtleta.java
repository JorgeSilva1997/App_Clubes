package com.example.appclubes.ATLETA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ADMIN.Main_admin;
import com.example.appclubes.ADMIN.Regist_Admin;
import com.example.appclubes.CAMPEONATO.ChooseOptionCamp;
import com.example.appclubes.EQUIPA.AddEquipa;
import com.example.appclubes.ESCALAO.AddEscalao;
import com.example.appclubes.Info;
import com.example.appclubes.Perfil;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListAtleta extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String TipoUserEmail;
    private TextView tipoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listatleta);

        lista = (ListView)findViewById(R.id.listAtletas);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        Default();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Isto serve para receber o email do user logado no momento
        String email = auth.getCurrentUser().getEmail().toString();
        reference.child("users").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    TipoUserEmail = postSnapshot.child("name").getValue().toString();
                    //tipoUser.setText(TipoUserEmail);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////////////////////

        getMenuInflater().inflate(R.menu.menu_atleta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.OrderName)
        {

        }
        else if (id == R.id.OrderEscalao)
        {

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    public void OrderNome()
    {
    }

    public void Default()
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("atleta");
        FirebaseListOptions<Atleta> options = new FirebaseListOptions.Builder<Atleta>()
                .setLayout(R.layout.linha_atleta)
                .setQuery(query,Atleta.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoAtleta = v.findViewById(R.id.NomedoAtleta);
                TextView EscalaodoAtleta = v.findViewById(R.id.EscalaodoAtleta);

                Atleta atleta = (Atleta) model;

                NomedoAtleta.setText(atleta.getNome());
                EscalaodoAtleta.setText(atleta.getEscalao());
            }
        };

        lista.setAdapter(adapter);
    }
}
