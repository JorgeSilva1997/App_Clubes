package com.example.appclubes.ATLETA;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("keyAtleta", keyAtleta);
        result.put("nome", nome);
        result.put("escalao", escalao);

        return result;
    }

}