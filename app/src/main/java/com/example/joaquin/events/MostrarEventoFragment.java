package com.example.joaquin.events;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MostrarEventoFragment extends Fragment {

    private static final String ACTIVITY = "StartCreate";
    private TextView tv;
    private ImageView iv;
    public MostrarEventoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_eventos, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        Bundle args=getArguments();
        if(args!=null){
            updateView(args.getString("id"));
        }
    }

    public void updateView(String id){
        //leer de la base de datos.
        BBDDSQLiteHelper usdbh =
                new BBDDSQLiteHelper(getActivity(), Environment.getExternalStorageDirectory()+"/Events.db", null, 1);
        //instancia la db.
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] argsID = new String[] {id};
        Cursor cursor = db.rawQuery(" SELECT * FROM evento WHERE id=? ", argsID);

        //Comprobar que existe
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            LinearLayout layoutPrincipal = (LinearLayout) getActivity().findViewById(R.id.mostrarEventoLayout);
            layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
            do{
                String nombreEvento = cursor.getString(cursor.getColumnIndex("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                String inicio = cursor.getString(cursor.getColumnIndex("inicio"));
                String fin = cursor.getString(cursor.getColumnIndex("fin"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String localidad = cursor.getString(cursor.getColumnIndex("localidad"));
                String codPostal = cursor.getString(cursor.getColumnIndex("cod_postal"));
                String provincia = cursor.getString(cursor.getColumnIndex("provincia"));
                String longitud = cursor.getString(cursor.getColumnIndex("longitud"));;
                String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
                //borramos la lista para que la vuelva a pintar
                layoutPrincipal.removeAllViewsInLayout();
                if (!nombreEvento.isEmpty()) crearElementoVista(nombreEvento, R.drawable.name_24, layoutPrincipal);
                if(!descripcion.isEmpty()) crearElementoVista(descripcion, R.drawable.createnew_24, layoutPrincipal);
                if(!inicio.isEmpty()) crearElementoVista(inicio, R.drawable.datefrom_24, layoutPrincipal);
                if(!fin.isEmpty()) crearElementoVista(fin, R.drawable.dateto_24, layoutPrincipal);
                if(!direccion.isEmpty()) crearElementoVista(direccion, R.drawable.road_24, layoutPrincipal);
                if(!localidad.isEmpty()) crearElementoVista(localidad, R.drawable.map_24, layoutPrincipal);
                if(!codPostal.isEmpty()) crearElementoVista(codPostal, R.drawable.regioncode_24, layoutPrincipal);
                if(!provincia.isEmpty()) crearElementoVista(provincia, R.drawable.worldwidelocation_24, layoutPrincipal);
                if(!longitud.isEmpty()) crearElementoVista(longitud, R.drawable.longitude_24, layoutPrincipal);
                if(!latitud.isEmpty()) crearElementoVista(latitud, R.drawable.latitude_24, layoutPrincipal);
            }while(cursor.moveToNext());
        }
    }

    public void crearElementoVista(String nombre, int rutaImage, LinearLayout layout){
        //creamos el segundo Layout.
        LinearLayout milayout = new LinearLayout(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //le pasamos los parametros y la orientación.
        milayout.setLayoutParams(params);
        milayout.setOrientation(LinearLayout.HORIZONTAL);
        //add textView
        tv = new TextView(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        tv.setText(nombre);
        iv = new ImageView(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        iv.setImageResource(rutaImage);
        //Le añadimos los parametros a los view.
        iv.setLayoutParams(params);
        tv.setLayoutParams(params);
        //añadimos al layout que hemos creado tanto el texto como la imagen.
        milayout.addView(iv);
        milayout.addView(tv);
        //le añadimos el layout que hemos creado al principal.
        layout.addView(milayout);
    }
}
