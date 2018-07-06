package com.example.maris.vehiclemanager.Adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Categories RecyclerView list
 */


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    List<Category> categoryList;
    onCategoriesAdapterInteractionListener mListener;

    public CategoriesAdapter(CategoriesAdapter.onCategoriesAdapterInteractionListener listener, List<Category> categoryList) {
        if(categoryList == null) categoryList = new ArrayList<>();
        this.categoryList = categoryList;
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories_list, parent, false);

        CategoriesAdapter.CategoriesViewHolder holder = new CategoriesAdapter.CategoriesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoriesViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getCategory());
        holder.type.setText(category.getType());

        holder.edit.setOnClickListener(view -> {
            mListener.onClickEdit(category);
        });
        holder.delete.setOnClickListener(view -> {
            mListener.onClickDelete(category);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type;
        public ImageView edit, delete;

        public CategoriesViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_category_name);
            type = itemView.findViewById(R.id.item_categories_txt);
            edit = itemView.findViewById(R.id.item_category_img_edit);
            delete = itemView.findViewById(R.id.item_category_img_delete);
        }
    }

    //Interaction
    public void setData(List<Category> categories) {
        categoryList = categories;
        notifyDataSetChanged();
    }
    public interface onCategoriesAdapterInteractionListener {
        void onClickEdit(Category category);
        void onClickDelete(Category category);
    }


}
