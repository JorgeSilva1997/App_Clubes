package com.example.appclubes.CONF;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraçãoFirebase {

    private static DatabaseReference reference;
    private static FirebaseAuth auth;


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
}
