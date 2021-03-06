package com.example.minerstats.Crypto;

public class Crypto {
    private String id;
    private String nom;

    public Crypto(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Crypto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
