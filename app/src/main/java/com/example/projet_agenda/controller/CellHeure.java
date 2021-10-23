package com.example.projet_agenda.controller;


public class CellHeure {
    //Les Variables a stocker dans une cellule

    private String Heure ;
    private String Activity;
    private String NomProf ;
    private String SalledeCour;
    private int id;
    private int NbreCelle ;


    //Constructeur de ma CellHeure
    public CellHeure(int id,String Activity,String Heure,String NomProf,String SalleDeCour){
        this.id = id;
        this.Activity = Activity;
        this.Heure = Heure;
        this.NomProf =NomProf;
        this.SalledeCour =SalleDeCour;
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
}
