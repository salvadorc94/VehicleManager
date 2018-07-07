package com.example.maris.vehiclemanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maris.vehiclemanager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.colorWithAlpha;
import static com.github.mikephil.charting.utils.ColorTemplate.createColors;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/
    /*private String mParam1;
    private String mParam2;
    */

    //Arreglo de colores para el PieChart //TODO: Escoger mejores colores para el piechart xd
    public static final int[] piechartColors ={
            rgb("#dd2c00"), rgb("#b71c1c"), rgb("#880e4f"), rgb("#7b1fa2"), rgb("#4527a0"),
            rgb("#1a237e"), rgb("#1565c0"), rgb("#006064"), rgb("#1b5e20"), rgb("#827717"),
            rgb("#4e342e"), rgb("#424242"), rgb("#263238")
    };

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Obtienes el chart
        PieChart piechart = v.findViewById(R.id.pie_chart);

        //TODO: llenar el PieEntry con las categorias de la App y asignar dinámicamente los values.
        //Entradas del chart una lista
        List<PieEntry> entries = new ArrayList<>();
        //Llenas la lista con PieEntry
        entries.add(new PieEntry(18.5f, "Category1"));
        entries.add(new PieEntry(26.7f, "Category2"));
        entries.add(new PieEntry(18.5f, "Category1"));
        entries.add(new PieEntry(26.7f, "Category2"));
        entries.add(new PieEntry(18.5f, "Category1"));

        //Se crea un dataset
        PieDataSet set = new PieDataSet(entries, "Expenses");
        /*Poner colores*/
        set.setColors(piechartColors);
        //Se asigna el dataset al piedata
        PieData data = new PieData(set);
        //Obtengo la descripcion
        Description description = piechart.getDescription();
        description.setEnabled(false);
        //Poner datos
        piechart.setData(data);
        //Obtener legendas para modificarlas
        Legend legend = piechart.getLegend();
        //Para no mostrarlas
        legend.setEnabled(false);
        //Refresh De manera dinámica
        piechart.notifyDataSetChanged();
        //Refrescar
        piechart.invalidate(); // refresh

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
