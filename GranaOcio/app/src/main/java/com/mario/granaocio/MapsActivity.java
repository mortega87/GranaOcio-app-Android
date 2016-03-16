package com.mario.granaocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mario.granaocio.database.DBHelper;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng coordenada;
    private double lat;
    private double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        definirToolbar();

        DBHelper mDB = new DBHelper(this);
        SQLiteDatabase db = mDB.getWritableDatabase();

        if(db != null) {
            Cursor cursor;

            Bundle bundle = getIntent().getExtras();
            String evento = bundle.getString("Titulo");
            cursor = db.rawQuery("select latitud, longitud from eventos where titulo='"+evento+"'", null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    lat = cursor.getDouble(0);
                    lng = cursor.getDouble(1);
                } while (cursor.moveToNext());
            }
        }

        //Cerramos la base de datos
        db.close();

        coordenada= new LatLng(lat,lng);
        //coordenada = new LatLng(39.481106,-0.340987);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 10));//10 es el zoom
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.addMarker(new MarkerOptions().position(coordenada).title("Marcador"));
    }



    public void definirToolbar(){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_mapa);

        toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setBackgroundResource(R.drawable.fondo_boton_v21);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

    }
}
