package com.example.joaquin.events;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float latitudAcon = 0;
    private float longitudAcon =0;
    private String nombreAcon;
    private float latitudEvt=0;
    private float longitudEvt=0;
    private String nombreEvt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences prefs = getSharedPreferences("Ajustes", Context.MODE_PRIVATE);

        String id = prefs.getString("id","");
        BBDDSQLiteHelper usdbh = new BBDDSQLiteHelper(this, Environment.getExternalStorageDirectory()+"/Events.db",null,1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] argsID = new String[]{id};
        Cursor cursorEvt = db.rawQuery("SELECT nombre,latitud,longitud FROM evento WHERE id_acontecimiento =? ", argsID);
        Cursor cursorAcon = db.rawQuery("SELECT nombre, latitud, longitud FROM acontecimiento WHERE id =? ", argsID);

        //vemos si existe
        if(cursorAcon.moveToFirst()){
            do {

                nombreAcon = cursorAcon.getString(cursorAcon.getColumnIndex("nombre"));
                longitudAcon = Float.parseFloat(cursorAcon.getString(cursorAcon.getColumnIndex("longitud")));
                latitudAcon = Float.parseFloat(cursorAcon.getString(cursorAcon.getColumnIndex("latitud")));

            }while (cursorAcon.moveToNext());
        }
        // marcador del acontecimiento actual
        LatLng aconActual = new LatLng(latitudAcon, longitudAcon);
        mMap.addMarker(new MarkerOptions().position(aconActual).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aconActual,16));
        if(cursorEvt.moveToFirst()){
            LatLng evt;
            do{
                nombreEvt = cursorEvt.getString(cursorEvt.getColumnIndex("nombre"));
                if(!cursorEvt.getString(cursorEvt.getColumnIndex("longitud")).isEmpty()&&
                        !cursorEvt.getString(cursorEvt.getColumnIndex("latitud")).isEmpty()){
                    longitudEvt=Float.parseFloat(cursorEvt.getString(cursorEvt.getColumnIndex("longitud")));
                    latitudEvt=Float.parseFloat(cursorEvt.getString(cursorEvt.getColumnIndex("latitud")));
                    evt = new LatLng(latitudEvt, longitudEvt);
                    mMap.addMarker(new MarkerOptions().position(evt).title(nombreEvt)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
            }while(cursorEvt.moveToNext());
        }
    }
}
