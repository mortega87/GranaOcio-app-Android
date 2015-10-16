package com.mario.granaocio;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class FragmentMusica extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter adapter;
    private FragmentCartaMusica fragmentCarta;


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

    public void setAdapter(){

        DataMusica contenido = new DataMusica();

        List<Evento> lista = new ArrayList<>();
        lista = contenido.getitems();

        mRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Crear un nuevo adaptador
        adapter = new AdapterMain(lista);
        mRecyclerView.setAdapter(adapter);

        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    //fragmentCarta = new FragmentCarta();
                    fragmentCarta = FragmentCartaMusica.newFragment(mRecyclerView.getChildPosition(child));
                    getFragmentManager().beginTransaction().add(R.id.fragment_principal, fragmentCarta).addToBackStack(null).commit();

                    return true;
                }


                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });


    }



}
