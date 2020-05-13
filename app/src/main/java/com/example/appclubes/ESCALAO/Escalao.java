package com.example.appclubes.ESCALAO;

public class Escalao {

    public  String keyEscalao;
    private String nome;

    public Escalao(){

    }

    public Escalao(String keyEscalao, String nome){

        this.keyEscalao = keyEscalao;
        this.nome = nome;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}


    public String getKeyEscalao() {return keyEscalao;}

    public void setKeyEscalao(String keyEscalao) {this.keyEscalao = keyEscalao;}

}


