package com.example.appclubes.EQUIPA;

public class Equipa {

    public  String keyAtleta;
    private String nome;

    public Equipa() {

    }

    public Equipa(String keyAtleta, String nome){

        this.keyAtleta = keyAtleta;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getKeyAtleta() {
        return keyAtleta;
    }

    public void setKeyAtleta(String keyAtleta) {
        this.keyAtleta = keyAtleta;
    }

}
