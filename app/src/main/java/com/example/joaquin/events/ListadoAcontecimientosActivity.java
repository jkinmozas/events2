package com.example.joaquin.events;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


public class ListadoAcontecimientosActivity extends AppCompatActivity {
    private static final String ACTIVITY = "StartCreate";
    private List<AcontecimientoItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //          .setAction("Action", null).show();
                startActivity(new Intent (getApplicationContext(), AnadirAcontecimientoActivity.class));
            }
        });
        items = new ArrayList<AcontecimientoItem>();
        items.add(new AcontecimientoItem("1", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("10", "Segundo acontecimiento"));
        items.add(new AcontecimientoItem("11", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("12", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("13", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("14", "Primer acontecimiento"));



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
        });

        // Se asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Se muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }

}
