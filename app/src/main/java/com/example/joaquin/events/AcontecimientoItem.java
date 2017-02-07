package com.example.joaquin.events;

import android.view.View;

/**
 * Created by Joaquin on 18/10/2016.
 */

public class AcontecimientoItem {
    private String id;
    private String nombre;
    private String fechaIncio;
    private String fechaFin;


    public AcontecimientoItem(String id, String nombre, String fechaIncio, String fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaIncio= fechaIncio;
        this.fechaFin= fechaFin;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(String fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}




