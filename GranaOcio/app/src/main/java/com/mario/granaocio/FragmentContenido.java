package com.mario.granaocio;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;


public class FragmentContenido extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter adapter;
    private FragmentCarta fragmentCarta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contenido, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.reciclador);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setAdapter();

    }

    public void setAdapter() {

        //Creamos un nuevo contenido
        DataContenido contenido = new DataContenido();

        //Recolecta de la lista del contenido.
        List<Evento> lista = contenido.getitems();

        mRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Crear un nuevo adaptador
        adapter = new AdapterMain(lista);
        mRecyclerView.setAdapter(adapter);


        //Definicion del listener onclick para menu y lanzamiento de fragments
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    //Se crea
                    fragmentCarta = FragmentCarta.newFragment(mRecyclerView.getChildPosition(child));
                    getFragmentManager().beginTransaction().add(R.id.fragment_principal, fragmentCarta).addToBackStack(null).commit();
                    //Log.d("HIJO",mRecyclerView.getChildPosition(child)+"");


                    return true;
                }


                return false;
            }


        });
    }

}