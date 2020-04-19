package com.example.appclubes.ATLETA;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.MyArrayAdapterAtletas;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAtletas extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    //ArrayList<String> arrayAtletas = new ArrayList<String>();
    public List<String> ListAtletas;
    ListView listaAtletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showatletas);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        listaAtletas = (ListView) findViewById(R.id.listAllAtletas);
        registerForContextMenu(listaAtletas);

        fillLista();
    }

    public void fillLista()
    {
        listaAtletas = findViewById(R.id.listAllAtletas);
        ListAtletas = new ArrayList<String>();

        //final ArrayList<String> atletas = new ArrayList<>();

        reference.child("atleta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListAtletas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {

                    String NameAtleta = postSnapshot.child("nome").getValue().toString();
                    String NameEscalao = postSnapshot.child("escalao").getValue().toString();
                    ListAtletas.add(NameAtleta);
                    ListAtletas.add(NameEscalao);

                    //ArrayAdapter dados = new ArrayAdapter(ShowAtletas.this, R.layout.support_simple_spinner_dropdown_item, atletas);
                    //dados.setDropDownViewResource(R.layout.lista_row2);
                    //listaAtletas.setAdapter(dados);

                    MyArrayAdapterAtletas itemsAdapter = new MyArrayAdapterAtletas(ShowAtletas.this, ListAtletas);
                    ((ListView) findViewById(R.id.listAllAtletas)).setAdapter(itemsAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

