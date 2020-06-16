package com.example.appclubes.USER;



public class User {

    private String numberId;
    private String name;
    private String password;
    private String email;
    private int tipo;
    private String KeyUser;
    private String cargo;

    public User() {

    }

    public User(String numberId, String name, String password, String email, int tipo, String KeyUser, String cargo){

        this.numberId = numberId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.tipo = tipo;
        this.KeyUser = KeyUser;
        this.cargo = cargo;
    }


    public String getNumberId() {
        return numberId;
    }
    public void setNumberId(String numberId){
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public String getKeyUser() {return KeyUser;}
    public void setKeyUser(String KeyUser) {this.KeyUser = KeyUser;}

    public String getCargo() {return cargo;}
    public void setCargo(String cargo) {this.cargo = cargo;}
}
