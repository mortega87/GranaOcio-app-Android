package com.mario.granaocio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME ="GranaOcio.db";
    public static final int DATABASE_VERSION = 1;
    //Sentencia SQL para crear la tabla de eventos
    String sqlCreate = "CREATE TABLE 'eventos' (" +
            "`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "`titulo` TEXT NOT NULL," +
            "`variedad` TEXT," +
            "`lugar` TEXT NOT NULL," +
            "`fecha` TEXT NOT NULL," +
            "`hora` TEXT NOT NULL," +
            "`precio` TEXT NOT NULL," +
            "`descripcion` TEXT NOT NULL," +
            "`latitud` DOUBLE NOT NULL," +
            "`longitud` DOUBLE NOT NULL," +
            "`id` TEXT NOT NULL" +
            ")";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }

    public void deleteTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS 'eventos'");

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aqui utilizamos directamente la opcion de
        //      eliminar la tabla anterior y crearla de nuevo vacia con el nuevo formato.
        //      Sin embargo lo normal sera que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este metodo deberia ser mas elaborado.

        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS eventos");

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}
