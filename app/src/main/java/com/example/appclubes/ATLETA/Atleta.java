package com.example.appclubes.ATLETA;

public class Atleta {

    public  String id;
    public  String nome;
    public  String escalao;

    public Atleta(){

    }

    public Atleta(String id, String nome, String escalao){

        this.id = id;
        this.nome = nome;
        this.escalao = escalao;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id=id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome=nome;}

    public String getEscalao() {return escalao;}
    public void setEscalao(String escalao) {this.escalao=escalao;}

}