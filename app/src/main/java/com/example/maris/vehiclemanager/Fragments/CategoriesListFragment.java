package com.example.maris.vehiclemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.maris.vehiclemanager.Adapters.CategoriesAdapter;
import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCategoriesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesListFragment extends Fragment implements CategoriesAdapter.onCategoriesAdapterInteractionListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    //private String mParam1;
    //private String mParam2;

    private AppViewModel viewModel;
    private OnCategoriesListFragmentInteractionListener mListener;
    private CategoriesAdapter adapter;
    private RecyclerView recycler;


    public CategoriesListFragment() {
        // Required empty public constructor
    }

    public static CategoriesListFragment newInstance(String param1, String param2) {
        CategoriesListFragment fragment = new CategoriesListFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);

        adapter = new CategoriesAdapter(this,null);

        //Set data from database flowable
        viewModel.getAllCategories().subscribe(categories -> {
            adapter.setData(categories);
        });

        recycler = v.findViewById(R.id.categories_recycler_list);
        recycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(manager);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriesListFragmentInteractionListener) {
            mListener = (OnCategoriesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategoriesListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickEdit(Category category) {
    }

    @Override
    public void onClickDelete(Category category) {
    }

    public interface OnCategoriesListFragmentInteractionListener{
    }
}
