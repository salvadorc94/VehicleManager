package com.example.maris.vehiclemanager.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFilterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";


    //private String mParam1;
    //private String mParam2;

    //ViewModel
    private AppViewModel viewModel;
    //Declarar Spinners
    Spinner date_spinner;
    Spinner category_spinner;
    //Declarar el calendario
    ImageView calendar;
    //Declarar el datePicker
    DatePickerDialog.OnDateSetListener mDataSetListener;
    //Declarar el texto
    EditText date_text;

    private OnFragmentInteractionListener mListener;

    public DateFilterFragment() {
        // Required empty public constructor
    }

    public static DateFilterFragment newInstance(String param1, String param2) {
        DateFilterFragment fragment = new DateFilterFragment();
        /*
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        */
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Provider del ViewModel
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date_filter, container, false);

        date_spinner = v.findViewById(R.id.fragment_date_filter_date_spinner);
        category_spinner = v.findViewById(R.id.fragment_date_filter_category_spinner);
        calendar = v.findViewById(R.id.fragment_date_filter_img_calendar);
        date_text = v.findViewById(R.id.fragment_date_filter_edit_text_date);


        viewModel.getAllCategories()
                .first(new ArrayList<>())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((categories, throwable)->{
                    //Handler para hacer modificaciones de interfaz en un thread que no es el main.
                    Handler handler = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            // Any UI task, example
                            ArrayAdapter<Category> category_adapter = new ArrayAdapter<Category>(getContext(),  android.R.layout.simple_spinner_dropdown_item, categories);
                            category_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                            category_spinner.setAdapter(category_adapter);
                        }
                    };
                    handler.sendEmptyMessage(1);
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDataSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_text.setVisibility(View.VISIBLE);
                String date = dayOfMonth+"/"+month+"/"+year;
                date_text.setText(date);
            }
        };


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.date_filter_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        date_spinner.setAdapter(adapter);


        //Listener del item selected
        date_spinner.setOnItemSelectedListener(this);
        category_spinner.setOnItemSelectedListener(this);
        return v;
    }

    private void runOnUiThread(Runnable runnable) {
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

    //Métodos al implementar el Spinner TODO:añadir funcionalidad al cambiar items.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
