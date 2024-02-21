package com.example.projectandriod;

public class client {
    Integer phone;
    String nom, prenom, code;

    public client() {
    }

    public client(Integer phone, String nom, String prenom, String code) {
        this.phone = phone;
        this.nom = nom;
        this.prenom = prenom;
        this.code = code;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
