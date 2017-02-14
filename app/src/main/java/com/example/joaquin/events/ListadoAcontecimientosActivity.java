package com.example.joaquin.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;


public class ListadoAcontecimientosActivity extends AppCompatActivity {
    private static final String ACTIVITY = "StartCreate";
    private ArrayList<AcontecimientoItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isSelect = pref.getBoolean("guardarAcontecimiento", false);
        if (isSelect) {

            SharedPreferences prefs = getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
            String id = prefs.getString("id","");
            if (!id.equals("")){
                this.startActivity(new Intent(this, VerAcontecimientosActivity.class));
            }
        }
        setContentView(R.layout.activity_listado_acontecimientos);
        //creamos la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent (getApplicationContext(), AnadirAcontecimientoActivity.class));
            }
        });
        rellenarLista();


/*
        // Mostrar el RecyclerView en vertical
        // Se inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Se crea el Adaptador con los datos
        AcontecimientoAdapter adaptador = new AcontecimientoAdapter(items);

        // Se asocia el elemento con una acción al pulsar el elemento
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                MyLog.d("ACTIVITY", "Click en RecyclerView");
                int position = recyclerView.getChildAdapterPosition(v);
                Toast toast = Toast.makeText(ListadoAcontecimientosActivity.this, String.valueOf(position) + " " + items.get(position).getId() + "Nombre: " + items.get(position).getNombre(), Toast.LENGTH_SHORT);
                toast.show();
            }

        // Se asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Se muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));        });*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.acerca_de:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
            case R.id.configutation:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                this.startActivity(intent2);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }
    private void rellenarLista(){
        items = new ArrayList<AcontecimientoItem>();
        //creamos la bbdd
        BBDDSQLiteHelper usdbh = new BBDDSQLiteHelper(this, Environment.getExternalStorageDirectory()+"/Events.db", null, 1);
        //comprobamos que exista la BD
        if(usdbh == null){
            MyLog.i(ACTIVITY, "no exite acontecimiento");

        }else{
            SQLiteDatabase db = usdbh.getReadableDatabase();
            String [] args = new String[]{};
            Cursor cursor = db.rawQuery("SELECT id,nombre,inicio,fin FROM acontecimiento ORDER BY inicio DESC", args);

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            if (cursor.moveToFirst()){
                items.removeAll(items);
                do{
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String nombreAcon = cursor.getString(cursor.getColumnIndex("nombre"));
                    String inicioNoFormat = cursor.getString(cursor.getColumnIndex("inicio"));
                    //formato
                    SimpleDateFormat dateParse = new SimpleDateFormat("yyyymmddhhmm");
                    SimpleDateFormat dateFotmat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try{
                        date = dateParse.parse(inicioNoFormat);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    String inicio=dateFotmat.format(date);
                    String finNoFormat = cursor.getString(cursor.getColumnIndex("fin"));
                    try{
                        date = dateParse.parse(finNoFormat);

                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    String fin = dateFotmat.format(date);
                    items.add(new AcontecimientoItem(id,nombreAcon,inicio,fin));

                }while(cursor.moveToNext());
                //Adapter con los datos
                AcontecimientoAdapter adapter = new AcontecimientoAdapter(items);
                //Asocioamos un listener
                adapter.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int position = recyclerView.getChildAdapterPosition(v);
                        // Acción al pulsar el elemento
                        //Para pasar los datos al fichero de Ajustes
                        SharedPreferences prefs =
                                getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("id", items.get(position).getId());
                        //System.out.println(items.get(position));
                        //System.out.println(items.get(position).getId());
                        editor.commit();
                        //abrimos la nueva actividad
                        startActivity(new Intent(ListadoAcontecimientosActivity.this, VerAcontecimientosActivity.class));
                        MyLog.d(ACTIVITY, "Click en RecyclerView");

                    }
                });

                //relacionamos adapter al recycler
                recyclerView.setAdapter(adapter);
                //mostramos el rectcler en vertical
                recyclerView.setLayoutManager(new LinearLayoutManager(this));


            }else{
                Snackbar.make(recyclerView,"No hay acontecimientos", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        }
    }
    //LOG
    @Override
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

        super.onDestroy();
    }
    //FIN LOG



}
