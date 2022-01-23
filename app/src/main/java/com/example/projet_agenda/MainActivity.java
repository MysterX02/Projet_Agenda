package com.example.projet_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Path;

import javax.xml.transform.dom.DOMLocator;

public class MainActivity extends AppCompatActivity {
    Context context;
    String url1 = "https://emploidutemps.univ-reunion.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?resources=3300&projectId=3&calType=ical&lastDate=2022-04-26&firstDate=2022-01-26&#displayConfigId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
    }
    public void clickAgenda(View p){
        //Telechargement d'un fichier activer
        //pathname_Download : chemin du dossier contennant l'agenda telecharger
        String pathname_Download=Environment.getExternalStoragePublicDirectory("Download")+"/" + "Agendias";
        File f1 = new File(Environment.getExternalStoragePublicDirectory("Download"),"Agendias");
        //Si le dossier Agendias n'existe pas on creer celui ci
        if (!f1.exists()) {
            f1.mkdirs();
            Toast.makeText(this,"creation dossier",Toast.LENGTH_LONG).show();
        }
        //Recherche si l'agenda a deja etais telecharger en Amont
        File f = new File( pathname_Download+"/L3Info.ics");
        if(!f.isFile())
        {
            //Si il est pas telecharger on le telecharge
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
            request.setTitle("Info3.ics");
            request.setDescription("Telechargement en cours....");
            request.setDestinationInExternalPublicDir("Download","Agendias/L3Info.ics");
            DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this,pathname_Download,Toast.LENGTH_LONG).show();

        }else{
            //Sinon on fais Rien
            Toast.makeText(this, "Deja Telecharger",Toast.LENGTH_LONG).show();

        }

        //Lancement de l'affichage de l'agenda quand celui ci est telecharger
        Intent intent =new Intent(context,AgendaActivity.class);
        startActivity(intent);
    }
}
