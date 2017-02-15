package com.example.joaquin.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joaquin on 27/10/2016.
 */

public class AnadirAcontecimientoAsyncTask extends AsyncTask<String, String, String> {
    private static final String ACTIVITY="ListadoAcontecimientosActivity";

    HttpURLConnection urlConnection;
    String id;
    Context myContext;
    View boton;
    ProgressBar progressBar;
    boolean existAcon = true;

    public AnadirAcontecimientoAsyncTask(String id,Context myContext,ProgressBar progressBar, View boton){

        this.id=id;
        this.myContext=myContext;
        this.progressBar=progressBar;
        this.boton=boton;
    }
    @Override
    protected void onPreExecute(){
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(String... args) {
        MyLog.d("doInBackground","background");
        StringBuilder result = new StringBuilder();


        try {
            MyLog.d("ID"," "+id);
            //Accedemos a nuestra url pasandole una id
            URL url = new URL("http://events.hol.es/api/v1/acontecimiento/"+id);
            //Abrimos la conexióncon la url.
            urlConnection = (HttpURLConnection) url.openConnection();
            //creamos la entrada del stream con un nuevo buffer dandole la conexión
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //leemos el el stream con un buffer.
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //si hay stream va leyendo y añadiendo en line.
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
//obtenemos el JSON
            JSONObject jsonCompleto = new JSONObject(result.toString());
            if (jsonCompleto.has("acontecimiento")) {
                //Hacemos la conexión con la bbdd.
                BBDDSQLiteHelper usbdh =
                        new BBDDSQLiteHelper(this.myContext, Environment.getExternalStorageDirectory()+"/Events.db", null, 1);
                //instancia la bd.
                SQLiteDatabase bd = usbdh.getWritableDatabase();

                //Si hemos abierto correctamente la base de datos entramos a comprobar el json
                if(bd != null)
                {
                    JSONObject jsonAcontecimiento = new JSONObject(jsonCompleto.getString("acontecimiento"));
                    String nombreAcontecimiento = (jsonAcontecimiento.has("nombre") ? jsonAcontecimiento.getString("nombre") : "");
                    String organizador = (jsonAcontecimiento.has("organizador") ? jsonAcontecimiento.getString("nombre") : "");
                    String descripcion = (jsonAcontecimiento.has("descripcion") ? jsonAcontecimiento.getString("descripcion") : "");
                    String tipo = (jsonAcontecimiento.has("tipo") ? jsonAcontecimiento.getString("tipo") : "");
                    String portada = (jsonAcontecimiento.has("portada") ? jsonAcontecimiento.getString("portada") : "");
                    String inicio = (jsonAcontecimiento.has("inicio") ? jsonAcontecimiento.getString("inicio") : "");
                    String fin = (jsonAcontecimiento.has("fin") ? jsonAcontecimiento.getString("fin") : "");
                    String direccion = (jsonAcontecimiento.has("direccion") ? jsonAcontecimiento.getString("direccion") : "");
                    String localidad = (jsonAcontecimiento.has("localidad") ? jsonAcontecimiento.getString("localidad") : "");
                    String codPostal = (jsonAcontecimiento.has("codPostal") ? jsonAcontecimiento.getString("codPostal") : "");
                    String provincia = (jsonAcontecimiento.has("provincia") ? jsonAcontecimiento.getString("provincia") : "");
                    String longitud = (jsonAcontecimiento.has("longitud") ? jsonAcontecimiento.getString("longitud") : "");
                    String latitud = (jsonAcontecimiento.has("latitud") ? jsonAcontecimiento.getString("latitud") : "");
                    String telefono = (jsonAcontecimiento.has("telefono") ? jsonAcontecimiento.getString("telefono") : "");
                    String email = (jsonAcontecimiento.has("email") ? jsonAcontecimiento.getString("email") : "");
                    String web = (jsonAcontecimiento.has("web") ? jsonAcontecimiento.getString("web") : "");
                    String facebook = (jsonAcontecimiento.has("facebook") ? jsonAcontecimiento.getString("facebook") : "");
                    String twitter = (jsonAcontecimiento.has("twitter") ? jsonAcontecimiento.getString("twitter") : "");
                    String instagram = (jsonAcontecimiento.has("instagram") ? jsonAcontecimiento.getString("instagram") : "");
                    //Después de comprobar el JSON añadimos a la base de datos la row con el campo ya insertado. si ya existe el id, lo elimina y lo vuelve a insertar.
                    //borramosel acontecimiento de la base de datos.
                    bd.execSQL("DELETE FROM `acontecimiento` WHERE id='"+id+"';");
                    //Insertamos los datos en la tabla Usuarios
                    bd.execSQL("INSERT INTO `acontecimiento` (`id`, `nombre`, `organizador`, `descripcion`, " +
                            "`tipo`, `portada`, `inicio`, `fin`, `direccion`, `localidad`, `cod_postal`, `provincia`," +
                            " `longitud`, `latitud`, `telefono`, `email`, `web`, `facebook`, `twitter`, `instagram`) " +
                            "VALUES ('"+id+"', '"+nombreAcontecimiento+"','"+organizador+"', '"+descripcion+"', '"+tipo+"', " +
                            "'"+portada+"', '"+inicio+"', '"+fin+"', '"+direccion+"', '"+localidad+"', '"+codPostal+"', '"+provincia+"', " +
                            "'"+longitud+"', '"+latitud+"', '"+telefono+"', '"+email+"', '"+web+"', '"+facebook+"', '"+twitter+"'," +
                            "'"+instagram+"');");

                    MyLog.i("NuevoAcontecimiento-Acon", (jsonAcontecimiento.has("nombre")) ? jsonAcontecimiento.getString("nombre") : "No nombre");
                    //Recuperar array
                    if(!jsonCompleto.has("evento")){
                        MyLog.i("NuevoAcontecimiento-Acon", "No tiene evento. No es un error, pero mola verlo en la consola");
                    }else{
                        JSONArray jsonEventoArray = new JSONArray(jsonCompleto.getString("evento"));
                        //COMPROBAR QUE EXISTE EL JSONARRAY
                        for (int i = 0; i < jsonEventoArray.length(); i++) {
                            JSONObject jsoneventoObjeto = jsonEventoArray.getJSONObject(i);
                            String idEvento = (jsoneventoObjeto.has("id") ? jsoneventoObjeto.getString("id") : "");
                            String nombreEvento = (jsoneventoObjeto.has("nombre") ? jsoneventoObjeto.getString("nombre") : "");
                            String descripcionEvento  = (jsoneventoObjeto.has("descripcion") ? jsoneventoObjeto.getString("descripcion") : "");
                            String inicioEvento  = (jsoneventoObjeto.has("inicio") ? jsoneventoObjeto.getString("inicio") : "");
                            String finEvento = (jsoneventoObjeto.has("fin") ? jsoneventoObjeto.getString("fin") : "");
                            String direccionEvento = (jsoneventoObjeto.has("direccion") ? jsoneventoObjeto.getString("direccion") : "");
                            String localidadEvento = (jsoneventoObjeto.has("localidad") ? jsoneventoObjeto.getString("localidad") : "");
                            String codPostalEvento = (jsoneventoObjeto.has("codPostal") ? jsoneventoObjeto.getString("codPostal") : "");
                            String provinciaEvento = (jsonAcontecimiento.has("provincia") ? jsonAcontecimiento.getString("provincia"): "");
                            String longitudEvento = (jsoneventoObjeto.has("longitud") ? jsoneventoObjeto.getString("longitud") : "");
                            String latitudEvento = (jsonAcontecimiento.has("latitud") ? jsonAcontecimiento.getString("latitud"): "");
                            //DELETE DEL EVENTO
                            bd.execSQL("DELETE FROM `evento` WHERE id='"+idEvento+"';");
                            bd.execSQL("INSERT INTO `evento` (`id`, `id_acontecimiento`, `nombre`, `descripcion`, `inicio`, `fin`," +
                                    " `direccion`, `localidad`, `cod_postal`, `provincia`, `longitud`, `latitud`) VALUES" +
                                    "('"+idEvento+"', '"+id+"', '"+nombreEvento+"', '"+descripcionEvento+"', '"+inicioEvento+"', '" +
                                    finEvento+"', '"+ direccionEvento+"', '"+localidadEvento+"', '"+ codPostalEvento+"', '"+ provinciaEvento+"', '"+
                                    longitudEvento+"', '"+ latitudEvento+"')");
                            MyLog.i("NuevoAcontecimiento-Event", jsoneventoObjeto.getString("nombre"));

                        }
                    }
                }

                //Cerramos la base de datos
                bd.close();
            } else {
                MyLog.i("NuevoAcontecimiento-Acon", "error");
                this.existAcon = false;
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
//sacamos el json
        try {
            if(existAcon){
                SharedPreferences prefs =
                        myContext.getSharedPreferences("Ajustes",Context.MODE_PRIVATE);
                //pasamos la id al shared
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("id", id);
                editor.commit();
                myContext.startActivity(new Intent(myContext, VerAcontecimientosActivity.class));
                //cerrar el activity.
                ((Activity) myContext).finish();
                progressBar.setVisibility(View.INVISIBLE);
            }else{
                //le pasamos la vista del boton y mandamos el mensaje
                Snackbar.make(boton, "id de acontecimiento no encontradada.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();

        }


    }

}
