package com.example.appclubes.CONF;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "app.preferences";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String EMAIL_USER_LOGIN = "email_user_login";
    private final String PASS_USER_LOGIN = "pass_user_login";


    public Preferences(Context contextParametro)
    {
        context = contextParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void GuardarCredencias(String email, String password)
    {
        editor.putString(EMAIL_USER_LOGIN, email);
        editor.putString(PASS_USER_LOGIN, password);
        editor.commit();
    }


    public String getEmailUserLogin()
    {
        return preferences.getString(EMAIL_USER_LOGIN, null);
    }

    public String getPassUserLogin()
    {
        return preferences.getString(PASS_USER_LOGIN, null);
    }
}
