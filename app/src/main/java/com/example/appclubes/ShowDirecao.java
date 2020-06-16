package com.example.appclubes;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ADMIN.Users;
import com.example.appclubes.EQUIPA.Equipa;
import com.example.appclubes.USER.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShowDirecao extends AppCompatActivity {

    ListView lista;
    FirebaseListAdapter adapter;
    int help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdirecao);

        lista = (ListView)findViewById(R.id.listDirecao);
        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setLayout(R.layout.linha_direcao)
                .setQuery(query,User.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoUser = v.findViewById(R.id.NomedoUser);
                TextView EmaildoUser = v.findViewById(R.id.EmaildoUser);
                TextView NumerodoUser = v.findViewById(R.id.NumerodoUser);
                TextView CargodoUser = v.findViewById(R.id.CargodoUser);


                User users = (User) model;
                help = users.getTipo();
                if (help == 1) {
                    NomedoUser.setText(users.getName());
                    EmaildoUser.setText(users.getEmail());
                    NumerodoUser.setText(users.getNumberId());
                    CargodoUser.setText(users.getCargo());
                }
                else {

                }
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
}
