package com.example.appclubes.ESCALAO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.EQUIPA.AddEquipa;
import com.example.appclubes.EQUIPA.ChooseEquipa;
import com.example.appclubes.EQUIPA.ListEquipa;
import com.example.appclubes.R;

public class ChooseEscalao extends AppCompatActivity {

    private Button btnaddescalao;
    private Button btnlistescalao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose3);

        btnaddescalao = (Button)findViewById(R.id.btnaddescalao);
        btnlistescalao = (Button)findViewById(R.id.btnlistescalao);

    }


    public void btnaddescalao(View view)
    {
        Intent intent = new Intent(ChooseEscalao.this, AddEscalao.class);
        startActivity(intent);
        finish();
    }

    public void btnlistescalao(View view)
    {
        Intent intent = new Intent(ChooseEscalao.this, ListEscalao.class);
        startActivity(intent);
        finish();
    }


}
