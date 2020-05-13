package com.example.appclubes.ATLETA;

public class Atleta {

    public  String keyAtleta;
    public  String nome;
    public  String escalao;

    public Atleta(){

    }

    public Atleta(String keyAtleta, String nome, String escalao){

        this.keyAtleta = keyAtleta;
        this.nome = nome;
        this.escalao = escalao;
    }

    public String getKeyAtleta() {return keyAtleta;}
    public void setKeyAtleta(String keyAtleta) {this.keyAtleta=keyAtleta;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome=nome;}

    public String getEscalao() {return escalao;}
    public void setEscalao(String escalao) {this.escalao=escalao;}

}