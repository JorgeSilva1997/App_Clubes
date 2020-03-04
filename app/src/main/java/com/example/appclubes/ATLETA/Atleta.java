package com.example.appclubes.ATLETA;

public class Atleta {

    private String nome;
    private String escalao;

    public Atleta(){

    }

    public Atleta(String nome, String escalao){

        this.nome = nome;
        this.escalao = escalao;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome=nome;}

    public String getEscalao() {return escalao;}
    public void setEscalao(String escalao) {this.escalao=escalao;}

}
