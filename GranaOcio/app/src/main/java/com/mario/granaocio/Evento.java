package com.mario.granaocio;

import android.content.Context;

import java.util.Date;

/**
 * Created by Mario on 05/04/2015.
 */
public class Evento {

    private String evento;
    private String variedad;
    private int imagen;
    private String lugar;
    private String fecha;
    private String hora;
    private String precio;
    private String descripcion;
    private Context contexto;

    public Evento(String s, String v, int img, String l, String f, String h, String p, String d, Context c){

        this.evento = s;
        this.variedad = v;
        this.imagen = img;
        this.lugar = l;
        this.fecha = f;
        this.hora = h;
        this.precio = p;
        this.descripcion = d;
        this.contexto = c;
    }

    public String getEvento(){return evento;}

    public String getVariedad(){return variedad;}

    public int getImagen(){return imagen;}

    public String getLugar(){
        return lugar;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora(){
        return hora;
    }

    public String getPrecio(){
        return precio;
    }

    public String getDescripcion() {return descripcion;}

    public Context getContexto(){return contexto;}

    public void setImagen(int img){this.imagen = img;}


}
