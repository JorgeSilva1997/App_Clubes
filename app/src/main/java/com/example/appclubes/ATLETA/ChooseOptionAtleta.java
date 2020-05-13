package com.example.appclubes.ATLETA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ADMIN.Main_admin;
import com.example.appclubes.R;

public class ChooseOptionAtleta extends AppCompatActivity {

    private Button btnaddatleta;
    private Button btnlistatleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        btnaddatleta = (Button)findViewById(R.id.addatleta);
        btnlistatleta = (Button)findViewById(R.id.listatleta);

    }

    public void btnlistatleta(View view)
    {
        Intent intent = new Intent(ChooseOptionAtleta.this, ListAtleta.class);
        startActivity(intent);
        finish();
    }

    public void btnaddatleta(View view)
    {
        Intent intent = new Intent(ChooseOptionAtleta.this, AddAtleta.class);
        startActivity(intent);
        finish();
    }
}
