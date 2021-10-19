package com.example.projet_agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_agenda.controller.AffPlanning;
import com.example.projet_agenda.controller.CellHeure;

public class AgendaActivity extends AppCompatActivity {
    Context context;
    Intent intent;
    ListView ListPlanning;
    private AffPlanning AffPlanning;
    CellHeure[] cellHeures;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        ListPlanning = findViewById(R.id.listPlanning);
        context = this;
        intent = getIntent();
        cellHeures = new CellHeure[24];
        for (int i=0;i<cellHeures.length;i++){
            cellHeures[i]= new CellHeure(i,"Je ne sais pas",i+" h :");

        }
        AffPlanning = new AffPlanning(this,cellHeures);
        ListPlanning.setAdapter(AffPlanning);
    }
    public void clickHome(View p){
        Intent intent =new Intent(context,MainActivity.class);
        startActivity(intent);
    }
}
