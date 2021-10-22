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

import java.io.IOException;
import java.io.InputStream;


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


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        context = this;
        intent = getIntent();
        calendarView = findViewById(R.id.calendarView);


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
        Calendar calendar = null;
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
            //for(int i = 0;i<)


            System.out.println(Jour+" "+Mois+" "+Anne+" A "+Heure+":"+Minute);




        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        ListPlanning = findViewById(R.id.listPlanning);


        cellHeures = new CellHeure[24];
        for (int i=0;i<cellHeures.length;i++){
            cellHeures[i]= new CellHeure(i,ip,i+" h :");

        }
        AffPlanning = new AffPlanning(this,cellHeures);
        ListPlanning.setAdapter(AffPlanning);
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
