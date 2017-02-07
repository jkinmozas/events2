package com.example.joaquin.events;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class EventosActivity extends AppCompatActivity {
    public static Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);


        if(findViewById(R.id.unique_fragment)!= null){
            if(savedInstanceState == null){
                ListadoEventsFragment listadoFrag = new ListadoEventsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.unique_fragment, listadoFrag).commit();

            }
        }
    }

}
