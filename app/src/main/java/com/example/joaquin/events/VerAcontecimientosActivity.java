package com.example.joaquin.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.sql.SQLOutput;

public class VerAcontecimientosActivity extends AppCompatActivity {
    private static final String ACTIVITY = "StartCreate";
    private TextView tv;
    private ImageView iv;
    private Context myContext;
    private String id;
    private Button maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_acontecimientos);
        myContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        maps = (Button) findViewById(R.id.mapas);
        maps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(myContext, MapsActivity.class));
            }
        });


        SharedPreferences prefs =
                getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
        id = prefs.getString("id","Error en Shared");
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabMostrarAcon);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            BBDDSQLiteHelper usbdh = new BBDDSQLiteHelper(myContext, Environment.getExternalStorageDirectory()+"/Events.db",null,1);
                SQLiteDatabase db = usbdh.getReadableDatabase();
                String[]argsID = new String[]{id};
                Cursor cursor = db.rawQuery("SELECT * FROM evento WHERE id_acontecimiento=?",argsID);
                if (cursor.moveToFirst()){
                    startActivity(new Intent(getApplicationContext(),EventosActivity.class));

                }   else{
                    Snackbar.make (view,"No hay eventos",Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }

            }
        });
        BBDDSQLiteHelper usdbh = new BBDDSQLiteHelper(this, Environment.getExternalStorageDirectory()+"/Events.db",null,1);
        //instancia de la bd
        SQLiteDatabase bd = usdbh.getReadableDatabase();

        String[] argsID= new String[]{id};
        Cursor cursor = bd.rawQuery("SELECT * FROM acontecimiento WHERE id=?", argsID);

        //comprobamos que existe 1 registro
        if (cursor.moveToFirst()){

            LinearLayout layoutPrincipal = (LinearLayout) findViewById(R.id.content_ver_acontecimientos);
            layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
            do{
                String nombreAcontecimiento = cursor.getString(cursor.getColumnIndex("nombre"));
                String organizador = cursor.getString(cursor.getColumnIndex("organizador"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                String portada = cursor.getString(cursor.getColumnIndex("portada"));
                String inicio = cursor.getString(cursor.getColumnIndex("inicio"));
                String fin = cursor.getString(cursor.getColumnIndex("fin"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String localidad = cursor.getString(cursor.getColumnIndex("localidad"));
                String codPostal = cursor.getString(cursor.getColumnIndex("cod_postal"));
                String provincia = cursor.getString(cursor.getColumnIndex("provincia"));
                String longitud = cursor.getString(cursor.getColumnIndex("longitud"));;
                String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String web = cursor.getString(cursor.getColumnIndex("web"));
                String facebook = cursor.getString(cursor.getColumnIndex("facebook"));
                String twitter = cursor.getString(cursor.getColumnIndex("twitter"));
                String instagram = cursor.getString(cursor.getColumnIndex("instagram"));

                if(!nombreAcontecimiento.isEmpty()) crearElementoVista(nombreAcontecimiento, R.drawable.name_24, layoutPrincipal);
                if(!organizador.isEmpty()) crearElementoVista(organizador, R.drawable.administrator_24, layoutPrincipal);
                if(!descripcion.isEmpty()) crearElementoVista(descripcion, R.drawable.createnew_24, layoutPrincipal);
                if(!tipo.isEmpty()) crearElementoVista(tipo, R.drawable.twosmartphones_24, layoutPrincipal);
                if(!portada.isEmpty()) crearElementoVista(portada, R.drawable.imagefile_24, layoutPrincipal);
                if(!inicio.isEmpty()) crearElementoVista(inicio, R.drawable.datefrom_24, layoutPrincipal);
                if(!fin.isEmpty()) crearElementoVista(fin, R.drawable.dateto_24, layoutPrincipal);
                if(!direccion.isEmpty()) crearElementoVista(direccion, R.drawable.road_24, layoutPrincipal);
                if(!localidad.isEmpty()) crearElementoVista(localidad, R.drawable.map_24, layoutPrincipal);
                if(!codPostal.isEmpty()) crearElementoVista(codPostal, R.drawable.regioncode_24, layoutPrincipal);
                if(!provincia.isEmpty()) crearElementoVista(provincia, R.drawable.worldwidelocation_24, layoutPrincipal);
               // if(!longitud.isEmpty()) crearElementoVista(longitud, R.drawable.longitude_24, layoutPrincipal);
               // if(!latitud.isEmpty()) crearElementoVista(latitud, R.drawable.latitude_24, layoutPrincipal);
                if(!telefono.isEmpty()) crearElementoVista(telefono, R.drawable.phone_24, layoutPrincipal);
                if(!email.isEmpty()) crearElementoVista(email, R.drawable.email_24, layoutPrincipal);
                if(!web.isEmpty()) crearElementoVista(web , R.drawable.link_24, layoutPrincipal);
                if(!facebook.isEmpty()) crearElementoVista(facebook, R.drawable.facebook_24, layoutPrincipal);
                if(!twitter.isEmpty()) crearElementoVista(twitter, R.drawable.twitter_24, layoutPrincipal);
                if(!instagram.isEmpty()) crearElementoVista(instagram, R.drawable.instagram_24,layoutPrincipal);
                if(longitud.isEmpty()||latitud.isEmpty()){
                maps.setVisibility(View.INVISIBLE);
                }
            }while(cursor.moveToNext());

        }

    }
    public void crearElementoVista (String nombre, int imgRuta, LinearLayout layout){
        LinearLayout milayout = new LinearLayout(new ContextThemeWrapper(this, R.style.AppTheme));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        milayout.setLayoutParams(params);
        milayout.setOrientation(LinearLayout.HORIZONTAL);
        //añadimos textview
        tv = new TextView(new ContextThemeWrapper(this, R.style.AppTheme));
        tv.setText(nombre);
        iv = new ImageView(new ContextThemeWrapper(this, R.style.AppTheme));
        iv.setImageResource(imgRuta);
        //ponemos los parametros a los view.
        iv.setLayoutParams(params);
        tv.setLayoutParams(params);
        //añadimos el texto y la imagen.
        milayout.addView(iv);
        milayout.addView(tv);
        //añadimos layout al principal
        layout.addView(milayout);

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
   /* @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, ListadoAcontecimientosActivity.class));
        this.finish();
    }*/
    @Override
    public boolean onNavigateUp() {
        this.startActivity(new Intent(this, ListadoAcontecimientosActivity.class));
        return true;
    }

}
