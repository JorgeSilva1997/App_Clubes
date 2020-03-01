package com.example.appclubes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    EditText numberId, pass;
    private DatabaseReference myRef;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        numberId = (EditText)findViewById(R.id.NumberId);
        pass = (EditText)findViewById(R.id.pass);
        myRef = FirebaseDatabase.getInstance().getReference().child("User");

    }

    public void btnRegist(View view){

        Intent intent = new Intent(Login.this, Regist.class);
        //intent.putExtra("ID", response.getInt("id"));
        //intent.putExtra("TIPO", response.getInt("tipo"));
        startActivity(intent);
    }
    String PASS;
    public void btnLogin(View view) {

      String NumberId = numberId.getText().toString();
      PASS = pass.getText().toString();

      if (myRef.child(NumberId) != null) {
      myRef.child(NumberId).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              User user = dataSnapshot.getValue(User.class);
              if (PASS.equals(user.getPassword()))
              {
                  Toast.makeText(Login.this, "Bem vindo", Toast.LENGTH_SHORT).show();
              }
              else
                  {
                      Toast.makeText(Login.this, "Login falhou!", Toast.LENGTH_SHORT).show();
                  }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    } else {
          Toast.makeText(Login.this, "User inexistente!", Toast.LENGTH_SHORT).show();
      }
    }

}
