package com.example.joaquin.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joaquin on 18/10/2016.
 */

public class AcontecimientoAdapter
        extends RecyclerView.Adapter<AcontecimientoAdapter.AcontecimientoViewHolder>
        implements View.OnClickListener {

    private List<AcontecimientoItem> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class AcontecimientoViewHolder
            extends RecyclerView.ViewHolder {

        private TextView TextView_nombre;
        private TextView TextView_id;
        private TextView TextView_fechaIncio;
        private TextView TextView_fechaFin;

        public AcontecimientoViewHolder(View itemView) {
            super(itemView);
            // TextView_id = (TextView) itemView.findViewById(R.id.TextView_id);
            TextView_nombre = (TextView) itemView.findViewById(R.id.TextView_nombre);
            TextView_fechaIncio = (TextView) itemView.findViewById(R.id.TextView_fecha_inicio);
            TextView_fechaFin = (TextView) itemView.findViewById(R.id.TextView_fecha_fin);

        }

        public void AcontecimientoBind(AcontecimientoItem item) {
            TextView_nombre.setText(item.getNombre());
            TextView_fechaIncio.setText(item.getFechaIncio());
            if(item.getFechaIncio()!=item.getFechaFin()){
                TextView_fechaFin.setText(item.getFechaFin());
            }
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public AcontecimientoAdapter(@NonNull List<AcontecimientoItem> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios para los elementos de la colección.
    // Infla la vista del layout y crea y devuelve el objeto ViewHolder
    @Override
    public AcontecimientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_listado_acontecimientos, parent, false);
        row.setOnClickListener(this);
        AcontecimientoViewHolder avh = new AcontecimientoViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(AcontecimientoViewHolder viewHolder, int position) {
        AcontecimientoItem item = items.get(position);
        viewHolder.AcontecimientoBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}