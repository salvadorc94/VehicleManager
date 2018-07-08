package com.example.maris.vehiclemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
public class HomeFragment extends Fragment
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/
    /*private String mParam1;
    private String mParam2;
    */
    //ViewModel
    private AppViewModel viewModel;
    //Entradas del PIECHART una lista de PieEntry
    List<PieEntry> entries = new ArrayList<>();
    private HashMap<Integer, Float> categoryTotals;
    private float total=0;
    private Date selectedDate;
    private String dateType;
    private Flowable<List<Expense>> expensesSource;
    private PieChart piechart;

    private DateFilterFragment filterFragment;
    private ExpensesListFragment expensesListFragment;

    //Arreglo de colores para el PieChart por si se quieren usar personalizados.
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
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Evitar que se abra el teclado
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (savedInstanceState == null) {
            filterFragment = new DateFilterFragment();
            expensesListFragment = new ExpensesListFragment();
        }
        else {
            filterFragment = (DateFilterFragment) getChildFragmentManager().getFragments().get(0);
            expensesListFragment = (ExpensesListFragment) getChildFragmentManager().getFragments().get(1);
        }

        getChildFragmentManager().beginTransaction()
                .replace(R.id.filter_fragment, filterFragment)
                .replace(R.id.expenses_list_fragment, expensesListFragment)
                .commit();



        //Obtienes el chart
        piechart = v.findViewById(R.id.pie_chart);
        updateExpenses();
        return v;
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

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
        updateExpenses();
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
        updateExpenses();
    }

    public void updateExpenses() {
        expensesSource = viewModel.getAllExpenses().map(expenses -> {
            String[] filters = getResources().getStringArray(R.array.date_filter_spinner);
            if (selectedDate == null) selectedDate = new Date();
            if (dateType == null) dateType = filters[0];
            ArrayList<Expense> filtered = new ArrayList<>();
            for (Expense expense : expenses) {
                Date expDate = expense.getDate();



                //Filter by Month
                if(dateType.equals(filters[0])) {
                    if (
                            expDate.getYear() == selectedDate.getYear() &&
                                    expDate.getMonth() == selectedDate.getMonth()
                            ) {
                        filtered.add(expense);
                    }
                }
                //Filter by Year
                else if (dateType.equals(filters[1])) {
                    if(expDate.getYear() == selectedDate.getYear()) {
                        filtered.add(expense);
                    }

                }
                //Filter by Day
                else if (dateType.equals(filters[2])) {
                    if(
                            expDate.getYear() == selectedDate.getYear() &&
                                    expDate.getMonth() == selectedDate.getMonth() &&
                                    expDate.getDate() == selectedDate.getDate()
                            )
                    {
                        filtered.add(expense);
                    }
                }

            }

            return filtered;
        });
        expensesSource.subscribe(expenses -> {
            categoryTotals = new HashMap<>();

            for (Expense expense : expenses) {
                Float previousValue = 0f;
                if (categoryTotals.containsKey(expense.getIdCat())) {
                    previousValue = categoryTotals.get(expense.getIdCat());
                }
                categoryTotals.put(expense.getIdCat(),previousValue+ expense.getCost());
            }

            viewModel.getAllCategories()
                    .first(new ArrayList<>())
                    .subscribe((categories, throwable) -> {
                        if (throwable == null) {
                            entries = new ArrayList<>();
                            for (Category category : categories) {
                                if (categoryTotals.containsKey(category.getIdCat())) {
                                    entries.add(new PieEntry(categoryTotals.get(category.getIdCat()), category.getCategory().toString()));
                                }
                            }
                            if (categoryTotals.containsKey(0)) {
                                //TODO: move 'default_no_category_name' to resources (Others)
                                entries.add(new PieEntry(categoryTotals.get(0), "Others"));
                            }

                            //Se crea un dataset
                            PieDataSet set = new PieDataSet(entries, "Expenses");
                            /*Poner colores*/
                            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
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
                        }

                        else {
                            //TODO: move to resources
                            Toast.makeText(this.getContext(), R.string.toast_unableto_loaddata, Toast.LENGTH_LONG).show();
                        }
                    });



        });
        expensesListFragment.setFlowableSource(expensesSource);
    }


  /*  @Override
    public void onTypeChanged(String dateType) {
        dateType = dateType;
        updateExpenses();
    }


    @Override
    public void onCategoryChanged(Category category) {
        selectedCategory =  category;
        updateExpenses();
    }

    @Override
    public void onDateChanged(Date date) {
        selectedDate = date;
        updateExpenses();
    }*/

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
