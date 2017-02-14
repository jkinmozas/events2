package com.example.joaquin.events;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

       //recoge el idioma
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idiomaACargar = prefs.getString("ListaDeLenguajes","default");
        //Pone el idioma
        Locale locale = new Locale(idiomaACargar);
        Configuration cfg = new Configuration();
        cfg.locale = locale;
        getBaseContext().getResources().updateConfiguration(cfg,getBaseContext().getResources().getDisplayMetrics());
        //Timer para el logo
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent (getApplicationContext(), ListadoAcontecimientosActivity.class));
                finish();
            }
        },2000);
    }

}
