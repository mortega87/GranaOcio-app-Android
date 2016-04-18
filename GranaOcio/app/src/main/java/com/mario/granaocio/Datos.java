package com.mario.granaocio;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import com.mario.granaocio.database.DBHelper;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Datos {
    static String id;
    static String objeto;
    static String variedad;
    static String lugar;
    static String fecha;
    static String hora;
    static String precio;
    static String descripcion;
    static ParseGeoPoint coordenadas;
    static Context contexto;
    static Double lat=null;
    static Double lng=null;
    static SQLiteDatabase db;
    static DBHelper mDB;
    static boolean dir;
    static File folder;

    static List<Evento> items = new ArrayList<>();

    public List<Evento> getitems(){
        return items;
    }

    public static void Update(Context c){

        contexto = c;
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Evento");
        query.orderByDescending("fecha");

        dir = crearDirectorio();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {

                    //Abrimos la base de datos en modo escritura
                    mDB = new DBHelper(contexto);
                    db = mDB.getWritableDatabase();

                    mDB.deleteTable(db);

                    for (int i = 0; i < list.size(); i++) {

                        ParseObject object = list.get(i);
                        id = object.getObjectId();
                        objeto = object.getString("titulo");
                        variedad = object.getString("variedad");
                        lugar = object.getString("lugar");
                        fecha = object.getString("fecha");
                        hora = object.getString("hora");
                        precio = object.getString("precio");
                        descripcion = object.getString("descripcion");
                        coordenadas = object.getParseGeoPoint("localizacion");

                        if (coordenadas != null) {
                            lat = coordenadas.getLatitude();
                            lng = coordenadas.getLongitude();
                        }

                        db.execSQL("insert into eventos (titulo, variedad, lugar, fecha, hora, precio, descripcion, latitud, longitud) " +
                                "values ('" + objeto + "', '" + variedad + "', '" + lugar + "', '" + fecha + "', '" + hora + "', '" + precio + "', '" + descripcion + "', '" + lat + "', '" + lng + "')");


                        retirarImagen(object);



                    }

                    //Cerramos la base de datos
                    db.close();


                } else Log.d("Update", "Error");
            }

        });



    }


    public static void retirarImagen(final ParseObject object){

        ParseFile imagen = (ParseFile)object.get("imagen");

        final String id = object.getObjectId();

            imagen.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {

                        try {

                            if (dir) {
                                writeFile(bytes, id);
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        // something went wrong
                        Log.d("something went wrong", "Error");
                    }


                }


            });

    }

    public static boolean crearDirectorio(){

        folder = new File(Environment.getExternalStorageDirectory().getPath() + "/img");

        boolean success = true;

        if (!folder.exists()) {
            success = folder.mkdirs();
        }

        return success;

    }

    public static void writeFile(byte[] data, String fileName) throws IOException{


        File f=new File(folder,fileName);

        try {//SAVING
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data);
            fos.flush();
            fos.close();
            //grabImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}//clase

