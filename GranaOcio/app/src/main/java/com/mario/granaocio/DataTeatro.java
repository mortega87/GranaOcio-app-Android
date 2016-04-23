package com.mario.granaocio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.mario.granaocio.database.DBHelper;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 06/05/2015.
 */
public class DataTeatro {

    static String objeto;
    static String lugar;
    static String fecha;
    static String hora;
    static String precio;
    static String descripcion;
    static String id;
    static Bitmap imagenTransformada;
    static List<Evento> items = new ArrayList<>();


    public List<Evento> getitems(){

        return items;
    }

    static void obtenerVariedad(Context contexto){
        //Abrimos la base de datos en modo escritura
        DBHelper mDB = new DBHelper(contexto);
        SQLiteDatabase db = mDB.getWritableDatabase();

        if(db != null) {
            Cursor cursor;

            cursor = db.rawQuery("select titulo, variedad, lugar, fecha, hora, precio, descripcion, id from eventos WHERE variedad='Teatro'", null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    objeto = cursor.getString(0);
                    lugar = cursor.getString(2);
                    fecha = cursor.getString(3);
                    hora = cursor.getString(4);
                    precio = cursor.getString(5);
                    descripcion = cursor.getString(6);
                    id = cursor.getString(7);

                    imagenTransformada = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/img/" + id);

                    items.add(new Evento(objeto, null, imagenTransformada, lugar, fecha, hora, precio, descripcion, contexto));

                } while (cursor.moveToNext());
            }
        }

        //Cerramos la base de datos
        db.close();
    }

}
