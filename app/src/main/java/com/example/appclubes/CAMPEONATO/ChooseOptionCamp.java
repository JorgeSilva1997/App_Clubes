package com.example.appclubes.CAMPEONATO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ATLETA.AddAtleta;
import com.example.appclubes.ATLETA.ChooseOptionAtleta;
import com.example.appclubes.ATLETA.ListAtleta;
import com.example.appclubes.R;

public class ChooseOptionCamp extends AppCompatActivity {

    private Button btnaddcampeonato;
    private Button btnlistcampeonato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose1);

        btnaddcampeonato = (Button)findViewById(R.id.btnaddcampeonato);
        btnlistcampeonato = (Button)findViewById(R.id.btnlistcampeonato);

    }

    public void btnlistcampeonato(View view)
    {
        Intent intent = new Intent(ChooseOptionCamp.this, ListCamp.class);
        startActivity(intent);
        finish();
    }

    public void btnaddcampeonato(View view)
    {
        Intent intent = new Intent(ChooseOptionCamp.this, AddCampeonato.class);
        startActivity(intent);
        finish();
    }
}
