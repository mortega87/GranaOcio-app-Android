package com.mario.granaocio;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCartaFlamenco extends Fragment implements View.OnClickListener{

    static int id;
    private Context contexto;
    private String titulo;

    public FragmentCartaFlamenco() {
        // Required empty public constructor
    }

    public static FragmentCartaFlamenco newFragment(int i){
        FragmentCartaFlamenco fragmentCarta = new FragmentCartaFlamenco();

        Bundle args = new Bundle();
        args.putInt("id", i);

        //Log.d("HIJO FRAG",i+"");
        id = i;
        fragmentCarta.setArguments(args);

        return fragmentCarta;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carta, container, false);

        DataFlamenco datos = new DataFlamenco();
        List<Evento> lista = datos.getitems();

        titulo = lista.get(id).getEvento();
        ImageView imageView = (ImageView)v.findViewById(R.id.imagen_evento);
        imageView.setImageResource(R.drawable.flamenco);
        ((TextView) v.findViewById(R.id.titulo_evento)).setText("Titulo: "+titulo);
        ((TextView) v.findViewById(R.id.descripcion_evento)).setText("Descripcion: " + lista.get(id).getDescripcion());
        ((TextView) v.findViewById(R.id.lugar_evento)).setText("Lugar: "+lista.get(id).getLugar());
        ((TextView) v.findViewById(R.id.hora_evento)).setText("Hora: " + lista.get(id).getHora());
        contexto = lista.get(id).getContexto();

        Button mapa;
        mapa = (Button) v.findViewById(R.id.boton_mapa);
        mapa.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent =  new Intent(contexto, MapsActivity.class);
        intent.putExtra("Titulo", titulo);
        startActivity(intent);
    }

}
