package com.mario.granaocio;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mario on 04/04/2015.
 */
public class AdapterMain extends RecyclerView.Adapter<AdapterMain.EventoViewHolder>{

    private List<Evento> items;

    public static class EventoViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView evento;
        public TextView lugar;
        public TextView fecha;
        public TextView hora;
        public TextView precio;



        public EventoViewHolder(View v){
            super(v);

            imagen = (ImageView)v.findViewById(R.id.imagen);
            evento = (TextView)v.findViewById(R.id.nombre);
            lugar = (TextView)v.findViewById(R.id.lugar);
            fecha = (TextView)v.findViewById(R.id.fecha);
            hora = (TextView)v.findViewById(R.id.hora);
            precio = (TextView)v.findViewById(R.id.precio);

        }


    }

    public AdapterMain(List<Evento> item){
        this.items=item;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        return new EventoViewHolder(v);


    }

    @Override
    public void onBindViewHolder(EventoViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.evento.setText(items.get(i).getEvento());
        viewHolder.lugar.setText("Lugar: " + items.get(i).getLugar());
        viewHolder.fecha.setText("Fecha: " + items.get(i).getFecha());
        viewHolder.hora.setText("Hora: " + items.get(i).getHora());
        viewHolder.precio.setText("Precio: " + items.get(i).getPrecio());
    }




}
