package com.example.minerstats.Minero;

public class Minero {

    private long id;
    private String nom;
    private int qtyGPU;
    private String id_crypto;
    private String ip_minero;

    public Minero(long id, String nom, int qtyGPU, String id_crypto, String ip_minero) {
        this.id = id;
        this.nom = nom;
        this.qtyGPU = qtyGPU;
        this.id_crypto = id_crypto;
        this.ip_minero = ip_minero;
    }

    public Minero() {
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

    public String getIp_minero() { return ip_minero; }

    public void setIp_minero(String ip_minero) { this.ip_minero = ip_minero; }
}
