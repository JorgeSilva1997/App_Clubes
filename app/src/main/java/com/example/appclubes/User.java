package com.example.appclubes;



public class User {

    private String numberId;
    private String name;
    private String password;
    private String email;
    private String tipo;

    public User() {

    }

    public User(String numberId, String name, String password, String email, String tipo){

        this.numberId = numberId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}
