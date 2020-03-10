package com.example.appclubes.CONVOCATORIA;

public class Convocatoria {

    private String escalao;
    private String campeonato;
    private String atleta;
    private String local;
    private String horaJogo;
    private String horaCedo;

    public Convocatoria() {

    }

    public Convocatoria(String escalao, String campeonato, String atleta, String local,
                        String horaJogo, String horaCedo)
    {
        this.escalao = escalao;
        this.campeonato = campeonato;
        this.atleta = atleta;
        this.local = local;
        this.horaJogo = horaJogo;
        this.horaCedo = horaCedo;
    }

    public String getEscalao() {
        return escalao;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public String getAtleta() {
        return atleta;
    }

    public String getLocal() {
        return local;
    }

    public String getHoraJogo() {
        return horaJogo;
    }

    public String getHoraCedo() {
        return horaCedo;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public void setAtleta(String atleta) {
        this.atleta = atleta;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setHoraJogo(String horaJogo) {
        this.horaJogo = horaJogo;
    }

    public void setHoraCedo(String horaCedo) {
        this.horaCedo = horaCedo;
    }
}
