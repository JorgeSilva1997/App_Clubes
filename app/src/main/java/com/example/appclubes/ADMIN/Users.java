package com.example.appclubes.ADMIN;

public class Users {

    public String keyUser;
    public String name;
    public String email;
    public int tipo;
    public String cargo;

    public Users() {

    }

    public Users(String keyUser, String name, String email, int tipo, String cargo) {

        this.keyUser = keyUser;
        this.name = name;
        this.email = email;
        this.tipo = tipo;
        this.cargo = cargo;
    }

    public String getKeyUser() {return keyUser;}

    public void setKeyUser(String keyUser) {this.keyUser = keyUser;}


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}


    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}


    public int getTipo() {return tipo;}

    public void setTipo(int tipo) {this.tipo = tipo;}


    public String getCargo() {return cargo;}

    public void setCargo(String cargo) {this.cargo = cargo;}
}
