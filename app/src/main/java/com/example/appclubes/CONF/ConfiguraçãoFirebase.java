package com.example.appclubes.CONF;

import com.example.appclubes.ATLETA.Atleta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConfiguraçãoFirebase {

    private static DatabaseReference reference;
    private static FirebaseAuth auth;

    // TESTE
    Boolean saved = null;
    ArrayList<Atleta> ArrayAtletas=new ArrayList<Atleta>();

    public ConfiguraçãoFirebase(DatabaseReference reference) {
        this.reference = reference;
    }

    public static DatabaseReference getReference(){

        if (reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static FirebaseAuth getAuth() {

        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }


    //WRITE
    public Boolean save(Atleta atleta)
    {
        if(atleta==null)
        {
            saved=false;
        }else
        {
            try
            {
                reference.child("atleta").push().setValue(atleta);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //READ
    public ArrayList<Atleta> retrieve()
    {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return ArrayAtletas;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        ArrayAtletas.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Atleta atleta=ds.getValue(Atleta.class);
            ArrayAtletas.add(atleta);
        }
    }
}
