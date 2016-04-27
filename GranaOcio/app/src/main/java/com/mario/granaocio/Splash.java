package com.mario.granaocio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by Mario on 27/08/2015.
 */
public class Splash extends Activity {

    protected boolean active = true;
    protected int splashTime = 3000;
    static Datos datos;
    private boolean b = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        Thread splashThread = new Thread(){
            @Override
            public void run(){
                try{
                    int waited = 0;
                    while(active && (waited < splashTime)){

                        SharedPreferences sh = getSharedPreferences("GranaOcio", MODE_PRIVATE);
                        b=sh.getBoolean("PrimeraVez",false);
                        if(!b) {
                            
                            datos.Update(getApplicationContext());
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putBoolean("PrimeraVez", true);
                            editor.apply();
                        }

                        sleep(200);
                        if(active){
                            waited += 100;
                        }

                    }
                } catch(InterruptedException e){

                } finally{
                    openApp();
                    //stop();
                }

            }
        };
        splashThread.start();
    }

    private void openApp(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

}
