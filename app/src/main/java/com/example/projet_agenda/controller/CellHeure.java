package com.example.projet_agenda.controller;


public class CellHeure {
    //Les Variables a stocker dans une cellule

    private String Heure ;
    private String Activity;
    private int id;
    private int NbreCelle ;


    //Constructeur de ma CellHeure
    public CellHeure(int id,String Activity,String Heure){
        this.id = id;
        this.Activity = Activity;
        this.Heure = Heure;
    }


    public String getHeure() {
        return Heure;
    }

    public void setHeure(String heure) {
        Heure = heure;
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
}
