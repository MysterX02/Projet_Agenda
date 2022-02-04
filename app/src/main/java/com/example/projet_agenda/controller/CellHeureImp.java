package com.example.projet_agenda.controller;

import com.example.projet_agenda.R;

import net.fortuna.ical4j.model.Calendar;

public class CellHeureImp {
    int HeureInt;
    int MinuteInt;
    String HeureStr;
    String MinuteStr;
    Calendar calendar;
    int TailleCalendrier;
    CellHeure[] CellHeure = new CellHeure[17];

    public CellHeureImp(int TailleCalendrier, Calendar calendar){
        this.calendar = calendar;
        this.TailleCalendrier = TailleCalendrier;
    }
    public CellHeure[] creationCellHeure(int dayOfMonth,int month){
        int NbreCoursJour=0;
        for(int i = 0;i<TailleCalendrier;i++) {
            String Cour = calendar.getComponents().get(i).toString();
            String[] lesMots = Cour.split(System.getProperty("line.separator"));
            String Debut = motSeparateur(lesMots[2], ":");
            String NomCours = motSeparateur(lesMots[4], ":");
            String Fin = motSeparateur(lesMots[3], ":");
            String[] ListDebut = jouretHeure(Debut);
            String Jour = ListDebut[2];
            String Mois = ListDebut[1];

            //Si l'utilisateur a appuyer sur un jour ou note le nombre de cour corespondant a ce Jour
            if (Integer.parseInt(Jour) == dayOfMonth && Integer.parseInt(Mois) == month) {
                NbreCoursJour++;
            }
        }
        //Création de la Liste des Cours du jour
        String[][] ListeCourJour = new String[NbreCoursJour][];
        int CompteurJour=0;
        //Ajout dans la Liste des cours du Jour
        for(int i = 0;i<TailleCalendrier;i++) {
            String Cour = calendar.getComponents().get(i).toString();
            String[] lesMots = Cour.split(System.getProperty("line.separator"));
            String Debut = motSeparateur(lesMots[2], ":");
            String[] ListDebut = jouretHeure(Debut);

            String Jour = ListDebut[2];
            String Mois = ListDebut[1];

            if (Integer.parseInt(Jour) == dayOfMonth && Integer.parseInt(Mois) == month) {
                ListeCourJour[CompteurJour] = lesMots;
                CompteurJour++;
            }
        }

        //Cree l'agenda du jour a parti de la premiere heure de cours

        for (int j=0;j<CellHeure.length;j++){
            int hJ = 7+j;
            CellHeure[j]= new CellHeure(hJ,"",hJ+" h :"," ","","", "#FFFFFF", false);

        }
        CompteurJour=0;

        for (int i=0;i<NbreCoursJour;i++) {
            String Debut = motSeparateur(ListeCourJour[i][2], ":");
            String Fin = motSeparateur(ListeCourJour[i][3], ":");
            String NomDeCours = motSeparateur(ListeCourJour[i][4], ":");
            String Info = motSeparateur(ListeCourJour[i][6], ":");
            String Salle = motSeparateur(ListeCourJour[i][5], ":");

            String[] ListDebut = jouretHeure(Debut);
            String[] ListFin = jouretHeure(Fin);
            String HeureD = ListDebut[3];
            String MinuteD = ListDebut[4];
            String HeureF = ListFin[3];
            String MinuteF = ListFin[4];

            //Convertir l'heure des cours en entier D = Début et F = Fin des cours
            int HeureChoisiD = Integer.parseInt(HeureD) + 4;
            int HeureChoisiF = Integer.parseInt(HeureF) + 4;
            int NbreHeure = HeureChoisiD - HeureChoisiF;

            //Postion dans la listView qui sera modifier grace a l'heure choisi on soustrait 5
            //On soustrait 5 car on commence l'affichage a partir de 6 heures
            int positionListViewD = HeureChoisiD - 7;
            int positionListViewF = HeureChoisiF - 7;
            //Affichage de la premiere Heure de cours
            CellHeure[positionListViewD] = new CellHeure(positionListViewD, NomDeCours, HeureChoisiD + "h" + MinuteD + " :", " ", nomProf(Info), Salle, colorCours(NomDeCours, Info), false);
            System.out.println(positionListViewF);
            CellHeure[positionListViewF - 1] = new CellHeure(positionListViewF, NomDeCours, HeureChoisiF + "h" + MinuteF + " :", " ", nomProf(Info), Salle, colorCours(NomDeCours, Info), true);
        }
            return CellHeure;
    }


    public String motSeparateur(String MotBase,String Delimiteur){
        String [] MotSeparer = MotBase.split(Delimiteur);
        return MotSeparer[1];
    }
    public String[] jouretHeure(String MotBase){
        String[] Res=new String[5];
        Res[0]=""+MotBase.charAt(0)+MotBase.charAt(1)+MotBase.charAt(2)+MotBase.charAt(3);
        Res[1]=""+MotBase.charAt(4)+MotBase.charAt(5);
        Res[2]=""+MotBase.charAt(6)+MotBase.charAt(7);
        Res[3]=""+MotBase.charAt(9)+MotBase.charAt(10);
        Res[4]=""+MotBase.charAt(11)+MotBase.charAt(12);
        return Res;
    }
    public String nomProf(String MotBase){
        String res="";
        String lines[] = MotBase.split("n");
        if(lines.length>=3){
            for (int j = 0;j<lines[3].length()-1;j++){
                res+=lines[3].charAt(j);
            }
        }
        return res;
    }
    public  String colorCours(String Summary,String Info){
        //Si on a pas de TD ou CM celle ci s'affiche en orange
        String res = "#E8872C";
        String linesInfo[] = Info.split("n");

        String linesSum[] = Summary.split("-");
        if(linesSum.length>=2){
            if(linesSum[1].charAt(1)=='C' && linesSum[1].charAt(2)=='T'){
                //Si on a Ct en rouge
                res = "#F9999F";
            }

        }
        else {
            //ici CM en Bleu et TD en vert
            if(linesInfo.length>=3){

                if(linesInfo[2].charAt(0)=='C' && linesInfo[2].charAt(1)=='M'){
                    res="#54C2FF";
                }
                if(linesInfo[2].charAt(0)=='T' && linesInfo[2].charAt(1)=='D'){
                    res="#66CC99";
                }
            }
        }

        return res;
    }
}

