package com.example.appclubes.CONVOCATORIA;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ATLETA.Atleta;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObterAtletas extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseListAdapter adapter;
    Map<String, Object> mapAtletas = new HashMap<>();
    ListView lista;
    CheckBox checkBoxAtleta;
    Button btn;
    public TextView NomedoAtleta;
    public String Key_ID, NomeDoAtleta;

    private String origem = "";
    private String escalao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custompopup);

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        lista = (ListView) findViewById(R.id.listAtletas);
        registerForContextMenu(lista);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        origem = bundle.getString("origem");

        if (origem.equals("AddAtleta")) {
            escalao = bundle.getString("escalao");
            Toast.makeText(ObterAtletas.this, "" + escalao, Toast.LENGTH_SHORT).show();
            Atletas(escalao);
            //ObterAtletas(escalao);
        }

        // Criar POP UP Window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        // Teste
        NomedoAtleta = (TextView) findViewById(R.id.NomeAtleta);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
/*
    public void ObterAtletas(String escalao){
    // Read from the database
        FirebaseDatabase.getInstance().getReference().child("atleta").orderByChild("escalao").equalTo(escalao).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                Key_ID = postSnapshot.child("keyAtleta").getValue().toString();
                NomeDoAtleta = postSnapshot.child("nome").getValue().toString();


                NomedoAtleta.setText(NomeDoAtleta);

            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
        }
    });

}
*/

    public void Atletas(String escalao) {
        Query query = FirebaseDatabase.getInstance().getReference().child("atleta").orderByChild("escalao").equalTo(escalao);
        FirebaseListOptions<Atleta> options = new FirebaseListOptions.Builder<Atleta>()
                .setLayout(R.layout.linha_obter_atleta)
                .setQuery(query, Atleta.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView NomedoAtleta = v.findViewById(R.id.NomeAtleta);

                Atleta atleta = (Atleta) model;
                NomedoAtleta.setText(atleta.getNome());

                //Writing Hashmap
                mapAtletas.put("nome", NomedoAtleta);
                NomeDoAtleta = NomedoAtleta.getText().toString();
                addListenerOnButtonClick(NomeDoAtleta);
            }

        };
        lista.setAdapter(adapter);
    }


        public void addListenerOnButtonClick(final String NomeDoAtleta){

            //Getting instance of CheckBoxes and Button from the activty_main.xml file
            Button btn = (Button) findViewById(R.id.AddAtleta);
            final String help = NomeDoAtleta;

            btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    /*for (String key : mapAtletas.keySet()) {

                        //Capturamos o valor a partir da chave
                        Object value = mapAtletas.get(key);
                        Toast.makeText(getApplicationContext(), key + " = " + value, Toast.LENGTH_LONG).show();
                    }*/


                    checkBoxAtleta=(CheckBox)findViewById(R.id.checkBoxAtleta);
                    if (checkBoxAtleta.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Boa !" + help, Toast.LENGTH_LONG).show();
                    }

                    else {
                        //Displaying the message on the toast
                        Toast.makeText(getApplicationContext(), "erro!", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }


            public void onCheckboxClicked(View view) {

                boolean checked = ((CheckBox) view).isChecked();

                if (checked) {
                    Toast.makeText(ObterAtletas.this, "Atleta selecionado" , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ObterAtletas.this, "Atleta deselecionado", Toast.LENGTH_SHORT).show();
                }
            }

/*    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBoxAtleta:
                str = checked ? "Atleta selecionado" : "Atleta não selecionado";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
*/
}


