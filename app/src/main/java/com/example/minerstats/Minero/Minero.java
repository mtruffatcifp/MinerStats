package com.example.minerstats.Minero;

public class Minero {

    private int id;
    private String nom;
    private int qtyGPU;
    private String id_crypto;

    public Minero(int id, String nom, int qtyGPU, String id_crypto) {
        this.id = id;
        this.nom = nom;
        this.qtyGPU = qtyGPU;
        this.id_crypto = id_crypto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQtyGPU() {
        return qtyGPU;
    }

    public void setQtyGPU(int qtyGPU) {
        this.qtyGPU = qtyGPU;
    }

    public String getId_crypto() {
        return id_crypto;
    }

    public void setId_crypto(String id_crypto) {
        this.id_crypto = id_crypto;
    }
}
