package com.example.appclubes.CONVOCATORIA;

import java.io.Serializable;

public class ConvocatoriaAtleta implements Serializable {

    private String NameAtleta;

    private boolean active;

    public ConvocatoriaAtleta(String NameAtleta)  {
        this.NameAtleta= NameAtleta;
        this.active= true;
    }

    public ConvocatoriaAtleta(String NameAtleta, boolean active)  {
        this.NameAtleta= NameAtleta;
        this.active= active;
    }

    public String getNameAtleta() {
        return NameAtleta;
    }

    public void setNameAtleta(String NameAtleta) {
        this.NameAtleta = NameAtleta;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.NameAtleta +" ("+ this.NameAtleta+")";
    }

}