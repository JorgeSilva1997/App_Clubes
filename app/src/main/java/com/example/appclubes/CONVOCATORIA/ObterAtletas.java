package com.example.appclubes.CONVOCATORIA;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ObterAtletas extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    
    ListView lista;
    ArrayList<String> arrayAtletas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custompopup);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        ObterAtletas();
    }

    public void ObterAtletas()
    {
        lista = (ListView)findViewById(R.id.lista);
        registerForContextMenu(lista);
        setContentView(R.layout.custompopup);

        reference.child("atleta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAtletas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    // Fazer IF tendo em conta o escalão selecionado
                    String NameAtleta = postSnapshot.child("nome").getValue().toString();
                    arrayAtletas.add(NameAtleta);

                    ArrayAdapter dados = new ArrayAdapter(ObterAtletas.this,  R.layout.lista_row, arrayAtletas);
                    ((ListView) findViewById(R.id.lista)).setAdapter(dados);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
