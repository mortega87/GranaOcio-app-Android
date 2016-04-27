package com.mario.granaocio;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import android.os.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private FragmentContenido recyclerviewFragment;
    private FragmentMusica recyclerMusica;
    private FragmentTeatro recyclerTeatro;
    private FragmentFlamenco recyclerFlamenco;
    private FragmentMuseos recyclerMuseos;
    public android.support.v7.widget.Toolbar toolbar;
    static Datos datos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //Lanzamos la vista principal





                DataContenido.obtenerVariedad(getApplicationContext(), "variedad");
                DataMusica.obtenerVariedad(getApplicationContext());
                DataFlamenco.obtenerVariedad(getApplicationContext());
                DataMuseos.obtenerVariedad(getApplicationContext());
                DataTeatro.obtenerVariedad(getApplicationContext());

                Log.d("MainActivity", "Oncreate");




        menuLateral(); //Cargamos el menu lateral
        actionBar();






    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MainActivity", "Onstart");

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MainActivity", "Onrestart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MainActivity", "Onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "Onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();Log.d("MainActivity", "Onstop");}

    @Override
    protected void onDestroy() {
        super.onDestroy();Log.d("MainActivity", "Ondestroy");}

    @Override
    public void onBackPressed(){

        FragmentManager fm = getFragmentManager();

        if(fm.getBackStackEntryCount() > 0)
            getFragmentManager().popBackStackImmediate();
        else
            finish();
    }


    //Menu lateral, recyclerView
    public void menuLateral() {

        //Definimos en String los nombres de secciones
        String Titles[] = {"Principal", "Música", "Teatro y Cine", "Flamenco y Danza", "Museos"};
        //Definimos los iconos a usar en un Array de iconos
        int Icons[] = {R.drawable.home, R.drawable.musica, R.drawable.teatro, R.drawable.baile, R.drawable.museos};

        String NAME = "GranaOcio";
        String DESCRIP = "Guía de Ocio de Granada";
        int PROFILE = R.drawable.granaociologo;

        //Definicion del menu deslizante DrawerLayout
        final DrawerLayout Drawer = (DrawerLayout)findViewById(R.id.drawer);
        //Definición del adaptador (Titulo, icono, nombre, descripción y perfil)
        final AdapterLateral adaptador = new AdapterLateral(Titles, Icons, NAME, DESCRIP, PROFILE);

        recycler = (RecyclerView) findViewById(R.id.lista_lateral);
        recycler.setHasFixedSize(true); //Tamaño fijo para el recyclerview


        //Definicion del listener onclick para menu y lanzamiento de fragments segun opcion
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){

                    Drawer.closeDrawers();

                    if(recyclerView.getChildPosition(child) == 1){

                        recyclerviewFragment = new FragmentContenido();
                        getFragmentManager().beginTransaction().add(R.id.fragment_principal, recyclerviewFragment).commit();

                        setActionBarPrincipal("Principal", toolbar);



                    }
                    if(recyclerView.getChildPosition(child) == 2){
                        recyclerMusica = new FragmentMusica();
                        getFragmentManager().beginTransaction().add(R.id.fragment_principal, recyclerMusica).commit();
                        setActionBarMusica("Música", toolbar);
                    }

                    if(recyclerView.getChildPosition(child) == 3){
                        recyclerTeatro = new FragmentTeatro();
                        getFragmentManager().beginTransaction().add(R.id.fragment_principal, recyclerTeatro).commit();
                        setActionBarTeatro("Teatro y Cine", toolbar);
                    }

                    if(recyclerView.getChildPosition(child) == 4){
                        recyclerFlamenco = new FragmentFlamenco();
                        getFragmentManager().beginTransaction().add(R.id.fragment_principal, recyclerFlamenco).commit();
                        setActionBarFlamenco("Flamenco y Danza", toolbar);
                    }

                    if(recyclerView.getChildPosition(child) == 5){
                        recyclerMuseos = new FragmentMuseos();
                        getFragmentManager().beginTransaction().add(R.id.fragment_principal, recyclerMuseos).commit();
                        setActionBarMuseos("Museos", toolbar);
                    }

                    return true;
                }

                return false;
            }



        });

        //Añadimos el separador para los items del menu
        recycler.addItemDecoration(new DividerItemDecoration(
                getApplicationContext()
        ));


        //Color fondo del menu completo
        recycler.setBackgroundColor(Color.WHITE);

        //Definicion del adaptador para el menu
        recycler.setAdapter(adaptador);



        lManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(lManager);
    }






    //Crea el toolbar junto al drawertoggle
    public void actionBar() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setActionBarPrincipal("Principal",toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

       // getSupportActionBar().hide(); //.show() para mostrar action bar
        actionBarDrawerToggle.syncState();

    }

    public void setActionBarPrincipal(CharSequence c, android.support.v7.widget.Toolbar toolbar){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        CharSequence text = c;

        TextView texto = (TextView) findViewById(R.id.texto_toolbar);
        texto.setText(text);

        toolbar.setBackgroundColor(getResources().getColor(R.color.color_principal));

    }

    public void setActionBarMusica(CharSequence c, android.support.v7.widget.Toolbar toolbar){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        CharSequence text = c;

        TextView texto = (TextView) findViewById(R.id.texto_toolbar);
        texto.setText(text);

        toolbar.setBackgroundColor(getResources().getColor(R.color.color_musica));

    }

    public void setActionBarFlamenco(CharSequence c, android.support.v7.widget.Toolbar toolbar){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        CharSequence text = c;

        TextView texto = (TextView) findViewById(R.id.texto_toolbar);
        texto.setText(text);

        toolbar.setBackgroundColor(getResources().getColor(R.color.color_flamenco));

    }

    public void setActionBarMuseos(CharSequence c, android.support.v7.widget.Toolbar toolbar){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        CharSequence text = c;

        TextView texto = (TextView) findViewById(R.id.texto_toolbar);
        texto.setText(text);

        toolbar.setBackgroundColor(getResources().getColor(R.color.color_museos));

    }

    public void setActionBarTeatro(CharSequence c, android.support.v7.widget.Toolbar toolbar){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        CharSequence text = c;

        TextView texto = (TextView) findViewById(R.id.texto_toolbar);
        texto.setText(text);

        toolbar.setBackgroundColor(getResources().getColor(R.color.color_teatro));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}