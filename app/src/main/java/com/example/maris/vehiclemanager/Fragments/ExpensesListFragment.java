package com.example.maris.vehiclemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Adapters.ExpensesAdapter;
import com.example.maris.vehiclemanager.EditAddExpenses;
import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnExpensesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpensesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesListFragment extends Fragment implements ExpensesAdapter.onExpensesAdapterInteractionListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";


    private AppViewModel viewModel;
    private OnExpensesListFragmentInteractionListener mListener;
    private ExpensesAdapter adapter;
    private RecyclerView recycler;

    public ExpensesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesListFragment.
     */
    public static ExpensesListFragment newInstance(String param1, String param2) {
        ExpensesListFragment fragment = new ExpensesListFragment();
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
        View v = inflater.inflate(R.layout.fragment_expenses_list, container, false);

        adapter = new ExpensesAdapter(this, null);

        recycler = v.findViewById(R.id.expenses_recycler_list);
        recycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(manager);


        return v;
    }

    public void setFlowableSource(Flowable<List<Expense>> expenseFlowable) {
        expenseFlowable.subscribe(expenses -> {
            adapter.setData(expenses);
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExpensesListFragmentInteractionListener) {
            mListener = (OnExpensesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnExpensesListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

 /*   @Override
    public void onClickPicture(Expense expense) {
        Toast.makeText(this.getContext(), "Show Picture of "+expense.getExpense(), Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onClickEdit(Expense expense) {
        Intent intent = new Intent(this.getContext(),EditAddExpenses.class);
        startActivity(intent);
//        Toast.makeText(this.getContext(), "Clicked edit of "+expense.getExpense(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickDelete(Expense expense) {
        viewModel.deleteExpenses(expense).subscribe();
//        Toast.makeText(this.getContext(), "Clicked delete of "+expense.getExpense(), Toast.LENGTH_SHORT).show();
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
    public interface OnExpensesListFragmentInteractionListener {

    }
}
