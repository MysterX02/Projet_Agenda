package com.example.projet_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import net.fortuna.ical4j.data.CalendarBuilder;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_agenda.controller.AffPlanning;
import com.example.projet_agenda.controller.CellHeure;


public class AgendaActivity extends AppCompatActivity {
    CalendarView calendarView;
    Context context;
    Intent intent;
    ListView ListPlanning;
    private AffPlanning AffPlanning;
    CellHeure[] cellHeures;
    CellHeure[] cellHeures2;
    Calendar calendar = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        context = this;
        intent = getIntent();

        AssetManager am =context.getAssets();
        InputStream is = null;
        try {
            //is = am.open("L3Info.ics");
            is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Agendias/L3Info.ics");


        } catch (IOException e) {
            e.printStackTrace();
        }
        CalendarBuilder builder = new CalendarBuilder();

        try {
            calendar = builder.build(is);
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        if (calendar != null) {


            PropertyList plist = calendar.getProperties();
            String sytemo = calendar.getComponents().get(2).toString();
            //Objectif trier les date d'apparition des cours dans l'ordre croissant
            String[] DateTrier;
            //Taille du Calendrier
            int TailleCalendrier = calendar.getComponents().size();
            //Parcours de tous les cours du Calendrier avoir le nombre de Jour a modifier
            for (int i = 0; i < TailleCalendrier; i++) {
                String Cour = calendar.getComponents().get(i).toString();

            }
            String ListeCours = "";

            String[] lesMots = sytemo.split(System.getProperty("line.separator"));

            String Debut = motSeparateur(lesMots[2], ":");
            String Fin = motSeparateur(lesMots[3], ":");
            String[] ListDebut = jouretHeure(Debut);
            String Anne = ListDebut[0];
            String Mois = ListDebut[1];
            String Jour = ListDebut[2];
            String Heure = ListDebut[3];
            String Minute = ListDebut[4];
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        calendarView = findViewById(R.id.calendarView);
        ListPlanning = findViewById(R.id.listPlanning);
        //Lorsque l'utilisateur clique sur une Date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                boolean Parcours = false;
                cellHeures = new CellHeure[17];
                cellHeures2 = new CellHeure[17];
                int NbreCoursJour = 0;
                if (calendar != null) {
                    //Taille du Calendrier
                    int TailleCalendrier = calendar.getComponents().size();
                    //Parcours de tous les cours du Calendrier avoir le nombre de Jour a modifier
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

                    for (int j=0;j<cellHeures.length;j++){
                        int hJ = 7+j;
                        cellHeures[j]= new CellHeure(hJ,"",hJ+" h :"," ","","","#F5F5F5", false);

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
                        int HeureChoisiD = Integer.parseInt(HeureD)+4;
                        int HeureChoisiF = Integer.parseInt(HeureF)+4;
                        int NbreHeure = HeureChoisiD-HeureChoisiF;

                        //Postion dans la listView qui sera modifier grace a l'heure choisi on soustrait 5
                        //On soustrait 5 car on commence l'affichage a partir de 6 heures
                        int positionListViewD = HeureChoisiD - 7;
                        int positionListViewF = HeureChoisiF - 7;
                        //Affichage de la premiere Heure de cours
                        cellHeures[positionListViewD]= new CellHeure(positionListViewD,NomDeCours,HeureChoisiD+"h"+MinuteD+" :"," ",nomProf(Info),Salle,colorCours(NomDeCours,Info), false);
                        System.out.println(positionListViewF);
                        cellHeures[positionListViewF-1]= new CellHeure(positionListViewF,NomDeCours,HeureChoisiF+"h"+MinuteF+" :"," ",nomProf(Info),Salle,colorCours(NomDeCours,Info), true);

                        //Affichage des Heures du debut du cours et de la fin de celle ci//

                        /////AFFECTATION HORRAIRE AVEC +4 VUE LE DECALAGE HORAIRE/////
                        /*
                        int HeureDChange = Integer.parseInt(HeureD)+4;
                        int HeureFChange = Integer.parseInt(HeureF)+4;
                        /////// Affichage du Debut de l'heure de cours trouver /////
                        cellHeures[HeureDChange]= new CellHeure(HeureDChange,NomDeCours,HeureDChange+"h"+MinuteD+" :"," ",nomProf(Info),Salle,colorCours(NomDeCours,Info), false);
                        int NbreHeure = HeureFChange-HeureDChange;

                        for(int j=HeureDChange+1;j<HeureDChange+NbreHeure;j++){

                            int heurefinaux = j+1;
                            if((HeureDChange+NbreHeure)-1==j){

                                cellHeures[j]= new CellHeure(j,"",j+"h 00:",heurefinaux+"h"+MinuteF," ","" ,colorCours(NomDeCours,Info), true);
                            }
                            else {
                                cellHeures[j]= new CellHeure(j,"",j+"h:o",heurefinaux+"h"+MinuteF," ","" ,colorCours(NomDeCours,Info), true);
                            }
                        }

                         */

                    }
                }


                AffPlanning = new AffPlanning(context,cellHeures);
                ListPlanning.setAdapter(AffPlanning);
            }
        });




    }
    public void clickHome(View p){
        Intent intent =new Intent(context,MainActivity.class);
        startActivity(intent);
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
