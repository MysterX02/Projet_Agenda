package com.example.projet_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import net.fortuna.ical4j.data.CalendarBuilder;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;

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
    Calendar calendar = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        context = this;
        intent = getIntent();



        String ip = "okok";
        AssetManager am =context.getAssets();
        InputStream is = null;
        try {
            is = am.open("ADECal.ics");
            ip = "iiiii";
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
            String sytemo=calendar.getComponents().get(2).toString();
            //Objectif trier les date d'apparition des cours dans l'ordre croissant
            String [] DateTrier ;
            //Taille du Calendrier
            int TailleCalendrier = calendar.getComponents().size();
            //Parcours de tous les cours du Calendrier avoir le nombre de Jour a modifier
            for(int i = 0;i<TailleCalendrier;i++){
                String Cour=calendar.getComponents().get(i).toString();

            }
            String ListeCours =  "";
            System.out.println("ok"+calendar.getComponents().size());
            String[] lesMots = sytemo.split(System.getProperty("line.separator"));

            String Debut = motSeparateur(lesMots[2],":");
            String Fin =motSeparateur(lesMots[3],":");
            String[] ListDebut = jouretHeure(Debut);
            String Anne = ListDebut[0];
            String Mois = ListDebut[1];
            String Jour = ListDebut[2];
            String Heure = ListDebut[3];
            String Minute = ListDebut[4];



            System.out.println(Jour+" "+Mois+" "+Anne+" A "+Heure+":"+Minute);




        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        calendarView = findViewById(R.id.calendarView);
        ListPlanning = findViewById(R.id.listPlanning);
        //Lorsque l'utilisateur clique sur une Date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                boolean Parcours = false;
                cellHeures = new CellHeure[24];
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

                        //Si l'utilasteur a appuyer sur un jour ou note le nombre de cour corespondant a ce Jour
                        if (Integer.parseInt(Jour) == dayOfMonth) {
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

                        if (Integer.parseInt(Jour) == dayOfMonth) {
                            ListeCourJour[CompteurJour] = lesMots;
                            CompteurJour++;
                        }
                    }
                    for (int j=0;j<cellHeures.length;j++){
                        cellHeures[j]= new CellHeure(j,"Rien",j+" h :","","");
                    }
                    CompteurJour=0;
                    for (int i=0;i<NbreCoursJour;i++) {
                        String Debut = motSeparateur(ListeCourJour[i][2], ":");
                        String NomDeCours = motSeparateur(ListeCourJour[i][4], ":");
                        String NomDeProf = motSeparateur(ListeCourJour[i][6], ":");
                        String Salle = motSeparateur(ListeCourJour[i][5], ":");

                        String[] ListDebut = jouretHeure(Debut);
                        String Heure = ListDebut[3];
                        int HeureChange = Integer.parseInt(Heure)+4;
                        cellHeures[HeureChange]= new CellHeure(HeureChange,NomDeCours,HeureChange+" h :",NomDeProf,Salle);
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
}
