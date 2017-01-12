package com.example.joaquin.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AboutActivity extends AppCompatActivity {
    private static final String ACTIVITY = "StartCreate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
    }
    //LOG
    protected void onStart() {
        MyLog.d(ACTIVITY,"onStart"); //creación del log del onStart
        super.onStart();
    }

    @Override
    protected void onResume() {
        MyLog.d(ACTIVITY,"onResume"); //creación del log del onResume
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyLog.d(ACTIVITY,"onPause"); //creación del log del onPause
        super.onPause();
    }

    @Override
    protected void onStop() {
        MyLog.d(ACTIVITY,"onStop"); //creación del log del onStop
        super.onStop();
    }

    @Override
    protected void onRestart() {
        MyLog.d(ACTIVITY,"onRestart"); //creación del log del onRestart
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        MyLog.d(ACTIVITY,"onDestroy"); //creación del log del onDestroy
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    //FIN LOG
}

