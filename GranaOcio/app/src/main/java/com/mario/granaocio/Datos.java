package com.mario.granaocio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mario.granaocio.database.DBHelper;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Datos {
    static String objeto;
    static String variedad;
    static String lugar;
    static String fecha;
    static String hora;
    static String precio;
    static String descripcion;
    static ParseGeoPoint coordenadas;
    static Context contexto;
    static int modificado;
    static List<Evento> items = new ArrayList<>();

    static ParseFile imagen;


    public List<Evento> getitems(){
        return items;
    }

    public static void Update(Context c){
        contexto = c;
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Evento");
        query.orderByDescending("fecha");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    //Abrimos la base de datos en modo escritura
                    DBHelper mDB = new DBHelper(contexto);
                    SQLiteDatabase db = mDB.getWritableDatabase();
                    for (int i = 0; i < list.size(); i++) {
                        Double lat = null;
                        Double lng = null;

                        ParseObject object = list.get(i);
                        objeto = object.getString("titulo");
                        variedad = object.getString("variedad");
                        lugar = object.getString("lugar");
                        fecha = object.getString("fecha");
                        hora = object.getString("hora");
                        precio = object.getString("precio");
                        descripcion = object.getString("descripcion");
                        coordenadas = object.getParseGeoPoint("localizacion");

                        if (object.getBoolean("modificado")){
                            modificado = 1;
                        }
                        else{
                            modificado = 0;
                        }

                        View v = View.inflate(contexto, R.layout.fragment_carta, null);
                        ParseImageView imageView = (ParseImageView)v.findViewById(R.id.imagen_evento);
                        ParseFile imagen = object.getParseFile("imagen");

                        if(imagen != null){
                            imageView.setParseFile(imagen);
                            //String prueba = imageView.toString();
                            //Log.d("Prueba",prueba);
                            /*imageView.loadInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if (e != null)
                                        e.printStackTrace();
                                }
                            });*/


                        }


                        if(coordenadas != null) {
                            lat = coordenadas.getLatitude();
                            lng = coordenadas.getLongitude();
                        }

                        db.execSQL("insert into eventos (titulo, variedad, lugar, fecha, hora, precio, descripcion, latitud, longitud, modificado) " +
                                "values ('" + objeto + "', '" + variedad + "', '" + lugar + "', '" + fecha + "', '" + hora + "', '" + precio + "', '" + descripcion + "', '" + lat + "', '" + lng + "', '" + modificado + "')"//);

                                + "where not exists (select titulo from eventos where titulo = '" + objeto + "')");

                    }
                    //Cerramos la base de datos
                    db.close();

                } else Log.d("Update", "Error");
            }
        });


    }
}
