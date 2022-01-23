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
import com.example.projet_agenda.controller.CellHeureImp;


public class AgendaActivity extends AppCompatActivity {
    CalendarView calendarView;
    Context context;
    Intent intent;
    ListView ListPlanning;
    private AffPlanning AffPlanning;
    CellHeure[] cellHeures;
    CellHeure[] cellHeures2;
    Calendar calendar = null;
    CellHeure[] CellHeure3;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        context = this;
        intent = getIntent();

        AssetManager am =context.getAssets();
        InputStream is = null;

        //////Lecture du fichier//////
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
        ////// Si le fichier est lu on intencie directement ///////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        calendarView = findViewById(R.id.calendarView);
        ListPlanning = findViewById(R.id.listPlanning);

        //Lorsque l'utilisateur clique sur une Date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                if (calendar != null) {
                    //Taille du Calendrier
                    int TailleCalendrier = calendar.getComponents().size();
                    //Classe pour Implementation de la cellule Heure du Jour
                    CellHeureImp CellHeureImp = new CellHeureImp(TailleCalendrier,calendar);
                    CellHeure3 = CellHeureImp.creationCellHeure(dayOfMonth,month);

                }
                //Affichage des cellule donner dans la list view
                AffPlanning = new AffPlanning(context,CellHeure3);
                ListPlanning.setAdapter(AffPlanning);
            }
        });
    }


    public void clickHome(View p){
        Intent intent =new Intent(context,MainActivity.class);
        startActivity(intent);
    }
}
