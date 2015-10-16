package com.mario.granaocio;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCarta extends Fragment implements View.OnClickListener {

    static int id;
    private Context contexto;
    private String titulo;

    public FragmentCarta() {
        // Required empty public
    }


    public static FragmentCarta newFragment(int i) {
        FragmentCarta fragmentCarta = new FragmentCarta();

        Bundle args = new Bundle();
        args.putInt("id", i);

        id = i;
        fragmentCarta.setArguments(args);

        return fragmentCarta;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carta, container, false);

        DataContenido datos = new DataContenido();
        List<Evento> lista = datos.getitems();

        titulo = lista.get(id).getEvento();
        ((TextView) v.findViewById(R.id.titulo_evento)).setText("Titulo: " + titulo);
        ((TextView) v.findViewById(R.id.descripcion_evento)).setText("Descripcion: " + lista.get(id).getDescripcion());
        ((TextView) v.findViewById(R.id.lugar_evento)).setText("Lugar: " + lista.get(id).getLugar());
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
        Intent intent = new Intent(contexto, MapsActivity.class);
        intent.putExtra("Titulo", titulo);
        startActivity(intent);
    }

}
