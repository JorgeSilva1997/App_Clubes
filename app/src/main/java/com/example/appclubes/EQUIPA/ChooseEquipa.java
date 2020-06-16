package com.example.appclubes.EQUIPA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CAMPEONATO.AddCampeonato;
import com.example.appclubes.CAMPEONATO.ChooseOptionCamp;
import com.example.appclubes.CAMPEONATO.ListCamp;
import com.example.appclubes.R;

public class ChooseEquipa extends AppCompatActivity {

    private Button btnaddequipa;
    private Button btnlistequipa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose2);

        btnaddequipa = (Button)findViewById(R.id.btnaddequipa);
        btnlistequipa = (Button)findViewById(R.id.btnlistequipa);

    }


    public void btnaddequipa(View view)
    {
        Intent intent = new Intent(ChooseEquipa.this, AddEquipa.class);
        startActivity(intent);
        finish();
    }

    public void btnlistequipa(View view)
    {
        Intent intent = new Intent(ChooseEquipa.this, ListEquipa.class);
        startActivity(intent);
        finish();
    }


}
