package com.mario.granaocio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mario.granaocio.database.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng coordenada;
    private double lat;
    private double lng;
    private double latCurrent;
    private double lngCurrent;
    private String strAdd;
    private Location l = null;


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

        ArrayList<Location> arrayLocation = new ArrayList<>();
        Location loc1 = new Location("test");
        loc1.setLatitude(coordenada.latitude);
        loc1.setLongitude(coordenada.longitude);
        arrayLocation.add(0, loc1);


        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 10));//10 es el zoom
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.addMarker(new MarkerOptions().position(coordenada).title("Marcador"));

        LocationManager location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = location.getProviders(true);

        for (int i = 0; i < providers.size(); i++) {
            l = location.getLastKnownLocation(providers.get(i));
            if (l != null) {
                latCurrent = l.getLatitude();
                lngCurrent = l.getLongitude();
                strAdd = getCompleteAddressString(latCurrent, lngCurrent);
                break;
            }
        }

        LatLng currentPoint = new LatLng(latCurrent,lngCurrent);
        Location loc2 = new Location("test2");
        loc2.setLatitude(currentPoint.latitude);
        loc2.setLongitude(currentPoint.longitude);
        arrayLocation.add(1,loc2);

        mMap.addMarker(new MarkerOptions().position(currentPoint).title("Marcador"));

        drawPrimaryLinePath(arrayLocation);

    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            "\n");
                }
                strAdd = strReturnedAddress.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private void drawPrimaryLinePath( ArrayList<Location> listLocsToDraw )
    {

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor( "#CC0000FF" ) );
        options.width( 5 );
        options.visible( true );

        for ( Location locRecorded : listLocsToDraw )
        {
            options.add( new LatLng( locRecorded.getLatitude(),
                    locRecorded.getLongitude() ) );
        }

        mMap.addPolyline( options );

    }


    public void definirToolbar(){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_mapa);

        toolbar.setNavigationIcon(R.drawable.back);

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
