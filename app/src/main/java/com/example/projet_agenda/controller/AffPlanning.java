package com.example.projet_agenda.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projet_agenda.R;

public class AffPlanning extends ArrayAdapter<CellHeure> {
    private final Context context;

    public AffPlanning(@NonNull Context context, CellHeure[] myCells) {
        super(context, R.layout.planning_cell,myCells);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View cellView=convertView;
        if(cellView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.planning_cell,parent,false);
        }
        TextView heure = cellView.findViewById(R.id.heureTv);
        TextView activity = cellView.findViewById(R.id.activityTv);
        TextView salle = cellView.findViewById(R.id.salleTv);
        TextView nomProf = cellView.findViewById(R.id.nomProfTv);
        LinearLayout ly = cellView.findViewById(R.id.back);
        CellHeure s = getItem(position);
        heure.setText(s.getHeure());
        activity.setText(s.getActivity());
        salle.setText(s.getSalledeCour());
        nomProf.setText(s.getNomProf());
        ly.setBackgroundColor(Color.parseColor(s.getColor()));

        return cellView;
    }
}
