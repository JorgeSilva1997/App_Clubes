package com.example.appclubes.CAMPEONATO;

public class Campeonato {

    public String keyCamp;
    public String nome;

    public Campeonato() {

    }

    public Campeonato(String keyCamp, String nome) {

        this.keyCamp = keyCamp;
        this.nome = nome;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getKeyCamp() {return keyCamp;}

    public void setKeyCamp(String keyCamp) {this.keyCamp = keyCamp;}
}
