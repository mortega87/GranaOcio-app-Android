package com.mario.granaocio;

import android.app.Activity;
import android.content.Intent;
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

                        datos.Update(getApplicationContext());

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
