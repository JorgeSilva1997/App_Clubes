package com.example.appclubes.ADMIN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.CAMPEONATO.AddCampeonato;
import com.example.appclubes.CAMPEONATO.ChooseOptionCamp;
import com.example.appclubes.CAMPEONATO.ListCamp;
import com.example.appclubes.R;

public class ChooseOptionAdmin extends AppCompatActivity {

    private Button btnaddcolaborador;
    private Button btnlistusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose4);

        btnaddcolaborador = (Button)findViewById(R.id.btnaddcolaborador);
        btnlistusers = (Button)findViewById(R.id.btnlistusers);

    }

    public void btnlistusers(View view)
    {
        Intent intent = new Intent(ChooseOptionAdmin.this, ListAllUser.class);
        startActivity(intent);
        finish();
    }

    public void btnaddcolaborador(View view)
    {
        Intent intent = new Intent(ChooseOptionAdmin.this, Regist_Admin.class);
        startActivity(intent);
        finish();
    }
}

