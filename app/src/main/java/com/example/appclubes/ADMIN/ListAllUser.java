package com.example.appclubes.ADMIN;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ATLETA.Atleta;
import com.example.appclubes.ATLETA.Edit_Atleta;
import com.example.appclubes.ATLETA.ListAtleta;
import com.example.appclubes.CAMPEONATO.Campeonato;
import com.example.appclubes.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ListAllUser extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;
    TextView NomedoUser;
    int help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listausers);


        lista = (ListView)findViewById(R.id.listUsers);
        registerForContextMenu(lista);

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseListOptions<Users> options = new FirebaseListOptions.Builder<Users>()
                .setLayout(R.layout.linha_users)
                .setQuery(query,Users.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoUser = v.findViewById(R.id.NomedoUser);
                TextView EmaildoUser = v.findViewById(R.id.EmaildoUser);
                TextView TipodoUser = v.findViewById(R.id.TipodoUser);

                Users users = (Users) model;

                help = users.getTipo();

                NomedoUser.setText(users.getName());
                EmaildoUser.setText(users.getEmail());

                if (help == 1){
                    TipodoUser.setText("Administrador");
                }
                else if (help == 0){
                    TipodoUser.setText("Normal");
                }

                //TipodoUser.setText(users.getTipo());
            }
        };

        lista.setAdapter(adapter);
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

    /////////////////// TESTE //////////////////////////


    //CONTEXT MENU//
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin_remove, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Context mContext = this;
        switch (item.getItemId()) {
            case R.id.remover:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*int itemPosition = info.position;
                                c.moveToPosition(itemPosition);
                                int id_contacto = c.getInt(c.getColumnIndex(Contrato.Contacto._ID));
                                deleteFromBD(id_contacto);*/
                                int itemPosition = info.position;
                                Toast.makeText(ListAllUser.this, "" + itemPosition, Toast.LENGTH_SHORT).show();
/*
                                Object model = null;
                                Users users = (Users) model;
                                String deleteKeyUser = users.getKeyUser();
                                Toast.makeText(ListAllUser.this, "" + deleteKeyUser, Toast.LENGTH_SHORT).show();
*/
                                Toast.makeText(ListAllUser.this, "" + NomedoUser, Toast.LENGTH_SHORT).show();
                                //String idremove = arrayEquipa.get(itemPosition).ID;
                                //deleteFromBD(idremove);
                                //filllista();
                            }
                        });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}