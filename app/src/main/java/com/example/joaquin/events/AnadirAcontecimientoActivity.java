package com.example.joaquin.events;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

public class AnadirAcontecimientoActivity extends AppCompatActivity  {
    Button buttonOk;
    EditText editTextID;
    public static Context context;
    final private int REQUEST_CODE_INTERNET = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_acontecimiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        final Button buttonOk = (Button) findViewById(R.id.buttonAnadir);
        editTextID = (EditText) findViewById(R.id.editTextID);
        context = this;
        //progresbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textoID = (EditText) findViewById(R.id.editTextID);
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textoID.getWindowToken(), 0);
                if (textoID.toString().isEmpty()) {
                    Snackbar.make(v, "Introduce un ID", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    MyLog.d("hola"," ENtro al if de empty");
                } else {
                    if (!connection()) {
                        Snackbar.make(v, "conexion no disponible", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        MyLog.d("hola"," ENtro al if de conexion");
                    } else{
                        MyLog.d("build", " " + Build.VERSION.SDK_INT);
                    // Comprobar permiso
                    int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);

                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        MyLog.d("hola"," ENtro al if de permiso");
                        new AnadirAcontecimientoAsyncTask(textoID.getText().toString(), context, progressBar, buttonOk).execute();
                    } else {

                            // Explicar permiso
                            if (ActivityCompat.shouldShowRequestPermissionRationale(AnadirAcontecimientoActivity.this, Manifest.permission.INTERNET)) {
                                Toast.makeText(context, "El permiso es necesario para utilizar el internet.", Toast.LENGTH_SHORT).show();
                            }

// Solicitar el permiso
                            ActivityCompat.requestPermissions(AnadirAcontecimientoActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);


                    }
                }
                }

            }
        });




    }
    public boolean connection(){
        ConnectivityManager conMgr=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected()||!netInfo.isAvailable()){
            Toast.makeText(context,"Conexion a internet no disponible",Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

}
