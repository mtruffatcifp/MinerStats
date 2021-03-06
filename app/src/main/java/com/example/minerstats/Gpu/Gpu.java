package com.example.minerstats.Gpu;

public class Gpu {
    private long id;
    private String nom;
    private long id_minero;

    public Gpu(long id, String nom, long id_minero) {
        this.id = id;
        this.nom = nom;
        this.id_minero = id_minero;
    }

    public Gpu() {
        this.id = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getId_minero() {
        return id_minero;
    }

    public void setId_minero(long id_minero) {
        this.id_minero = id_minero;
    }
}
