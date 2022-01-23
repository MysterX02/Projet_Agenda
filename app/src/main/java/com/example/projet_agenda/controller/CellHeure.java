package com.example.projet_agenda.controller;


public class CellHeure {
    //Les Variables a stocker dans une cellule

    private String HeureD ;
    private String HeureF ;
    private String Activity;
    private String NomProf ;
    private String SalledeCour;
    private int id;
    private int NbreCelle ;
    private String Color;
    private boolean Espacement;


    //Constructeur de ma CellHeure

    public CellHeure(int id, String Activity, String HeureD,String HeureF, String NomProf, String SalleDeCour, String Color, boolean Espacement){
        this.id = id;
        this.Activity = Activity;
        this.HeureD = HeureD;
        this.HeureF = HeureF;
        this.NomProf =NomProf;
        this.SalledeCour =SalleDeCour;
        this.Color =Color;
        this.Espacement = Espacement;
    }


    public String getHeure() {
        return HeureD;
    }

    public void setHeure(String heure) {
        HeureD = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbreCelle() {
        return NbreCelle;
    }

    public void setNbreCelle(int nbreCelle) {
        NbreCelle = nbreCelle;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getSalledeCour() {
        return SalledeCour;
    }

    public void setSalledeCour(String salledeCour) {
        SalledeCour = salledeCour;
    }

    public String getNomProf() {
        return NomProf;
    }

    public void setNomProf(String nomProf) {
        NomProf = nomProf;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public boolean isEspacement() {
        return Espacement;
    }

    public void setEspacement(boolean espacement) {
        Espacement = espacement;
    }

    public String getHeureF() {
        return HeureF;
    }

    public void setHeureF(String heureF) {
        HeureF = heureF;
    }
}
