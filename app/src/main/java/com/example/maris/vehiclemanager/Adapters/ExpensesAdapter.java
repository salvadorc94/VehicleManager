package com.example.maris.vehiclemanager.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for Expenses RecyclerView list
 */

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {

    List<Expense> expenseList;
    onExpensesAdapterInteractionListener mListener;


    public ExpensesAdapter(onExpensesAdapterInteractionListener listener, List<Expense> expenseList) {
        if(expenseList == null) expenseList = new ArrayList<>();
        this.expenseList = expenseList;
        mListener = listener;
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenses_list, parent, false);

        ExpensesViewHolder holder = new ExpensesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.name.setText(expense.getExpense());
        holder.cost.setText("$ "+expense.getCost());
        holder.date.setText(expense.getDate().toString());

        //TODO: set dynamic category color
        holder.category.setBackgroundColor(Color.parseColor("#ffa726"));

        holder.picture.setOnClickListener(view -> {
            mListener.onClickPicture(expense);
        });

        holder.edit.setOnClickListener(view -> {
            mListener.onClickEdit(expense);
        });
        holder.delete.setOnClickListener(view -> {
            mListener.onClickDelete(expense);
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, cost, date;
        public ImageView picture, edit, delete;
        public FrameLayout category;

        public ExpensesViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_expenses_txt_name);
            cost = itemView.findViewById(R.id.item_expenses_txt_cost);
            date = itemView.findViewById(R.id.item_expenses_txt_date);
        }
    }

    //Interaction

    public void setData(List<Expense> expenses) {
        expenseList = expenses;
        notifyDataSetChanged();
    }

    public interface onExpensesAdapterInteractionListener {
        void onClickPicture(Expense expense);
        void onClickEdit(Expense expense);
        void onClickDelete(Expense expense);
    }


}
