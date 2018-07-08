package com.example.maris.vehiclemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Adapters.VehiclesAdapter;
import com.example.maris.vehiclemanager.EditorAddVehicle;
import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnVehiclesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehiclesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehiclesListFragment extends Fragment implements VehiclesAdapter.onVehiclesAdapterInteractionListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";


    private AppViewModel viewModel;
    private OnVehiclesListFragmentInteractionListener mListener;
    private VehiclesAdapter adapter;
    private RecyclerView recycler;
    private FloatingActionButton add_vehicle;

    public VehiclesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehiclesListFragment.
     */
    public static VehiclesListFragment newInstance(String param1, String param2) {
        VehiclesListFragment fragment = new VehiclesListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vehicles_list, container, false);

        adapter = new VehiclesAdapter(this, null);

        add_vehicle = v.findViewById(R.id.fab_vehicles);
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditorAddVehicle.class);
                startActivity(intent);
            }
        });

        //Set data from database flowable
        viewModel.getAllVehicles().subscribe(vehicles -> {
            adapter.setData(vehicles);
        });

        recycler = v.findViewById(R.id.vehicles_recycler_list);
        recycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(manager);


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVehiclesListFragmentInteractionListener) {
            mListener = (OnVehiclesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnVehiclesListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickPicture(Vehicle vehicle) {
        Toast.makeText(this.getContext(), "Show Picture of "+vehicle.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickEdit(Vehicle vehicle) {
        Intent intent = new Intent(getContext(),EditorAddVehicle.class);
        intent.putExtra("carro",vehicle);
        startActivity(intent);

        //Toast.makeText(this.getContext(), "Clicked edit of "+vehicle.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickDelete(Vehicle vehicle) {
        //TODO: Validar si queda solo uno.
        viewModel.deleteVehicles(vehicle).subscribe();
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
    public interface OnVehiclesListFragmentInteractionListener {

    }
}
