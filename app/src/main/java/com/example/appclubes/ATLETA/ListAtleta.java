package com.example.appclubes.ATLETA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.Edit_Profile;
import com.example.appclubes.Perfil;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListAtleta extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String TipoUserEmail, key;
    private TextView tipoUser, NomedoAtleta, EscalaodoAtleta;
    Map<String, Object> mapAtletas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listatleta);

        lista = (ListView)findViewById(R.id.listAtletas);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        registerForContextMenu(lista);

        NomedoAtleta = (TextView)findViewById(R.id.NomedoAtleta);
        EscalaodoAtleta = (TextView)findViewById(R.id.EscalaodoAtleta);

        Default();

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

                String key = atleta.getKeyAtleta();

                //Writing Hashmap

                mapAtletas.put("nome", NomedoAtleta);
                mapAtletas.put("escalao", EscalaodoAtleta);
                mapAtletas.put("keyatleta", key);

            }
        };

        lista.setAdapter(adapter);
    }



    //CONTEXT MENU//
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Context mContext = this;
        switch (item.getItemId()) {
            case R.id.editar:

                int itemPosition = info.position;


                Toast.makeText(ListAtleta.this, "" + itemPosition, Toast.LENGTH_SHORT).show();


                for (String key : mapAtletas.keySet()) {

                //Capturamos o valor a partir da chave
                Object value = mapAtletas.get(key);
                System.out.println(key + " = " + value);
                    //Toast.makeText(ListAtleta.this, key + " = " + value, Toast.LENGTH_SHORT).show();

                    if (key == "keyatleta")
                    {
                        String keyAtleta = (String) value;
                        //Toast.makeText(ListAtleta.this, "" + keyAtleta, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ListAtleta.this, Edit_Atleta.class);
                        Bundle bundle = new Bundle();


                        bundle.putString("origem", "editAtleta");

                        bundle.putString("KeyAtleta", keyAtleta);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
            }

/*
                Intent intent = new Intent(ListAtleta.this, Edit_Atleta.class);
                Bundle bundle = new Bundle();

                String nome = NomedoAtleta.getText().toString();
                String escalao = EscalaodoAtleta.getText().toString();

                bundle.putString("origem", "editAtleta");

                bundle.putString("nome", nome);
                bundle.putString("escalao", escalao);
                bundle.putString("KeyAtleta", key);

                intent.putExtras(bundle);
                startActivity(intent);
                finish(); */
                return true;


            case R.id.remover:
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setCancelable(true);
//                builder.setMessage("Are you sure?");
//                builder.setPositiveButton("YES",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                /*int itemPosition = info.position;
//                                c.moveToPosition(itemPosition);
//                                int id_contacto = c.getInt(c.getColumnIndex(Contrato.Contacto._ID));
//                                deleteFromBD(id_contacto);*/
//                                int itemPosition = info.position;
//                                String idremove = arrayEquipa.get(itemPosition).ID;
//                                deleteFromBD(idremove);
//                                filllista();
//                            }
//                        });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



}
