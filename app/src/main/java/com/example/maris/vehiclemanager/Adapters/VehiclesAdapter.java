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

import com.example.maris.vehiclemanager.Model.Database.Vehicle;
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for Vehicles RecyclerView list
 */

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder> {

    List<Vehicle> vehicleList;
    onVehiclesAdapterInteractionListener mListener;


    public VehiclesAdapter(onVehiclesAdapterInteractionListener listener, List<Vehicle> vehicleList) {
        if(vehicleList == null) vehicleList = new ArrayList<>();
        this.vehicleList = vehicleList;
        mListener = listener;
    }

    @NonNull
    @Override
    public VehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicles_list, parent, false);

        VehiclesViewHolder holder = new VehiclesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);

        holder.name.setText(vehicle.getName());
        holder.plate.setText(vehicle.getPlate());
        //TODO: switch/convert to proper unit (preferences)
        holder.odometer.setText(vehicle.getOdometer()+" Km");


        holder.picture.setOnClickListener(view -> {
            mListener.onClickPicture(vehicle);
        });

        holder.edit.setOnClickListener(view -> {
            mListener.onClickEdit(vehicle);
        });
        holder.delete.setOnClickListener(view -> {
            mListener.onClickDelete(vehicle);
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public static class VehiclesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, plate, odometer;
        public ImageView picture, edit, delete;

        public VehiclesViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_vehicles_txt_name);
            plate = itemView.findViewById(R.id.item_vehicles_txt_plate);
            odometer = itemView.findViewById(R.id.item_vehicles_txt_odometer);

            picture = itemView.findViewById(R.id.item_vehicles_img_show_picture);
            edit = itemView.findViewById(R.id.item_vehicles_img_edit);
            delete = itemView.findViewById(R.id.item_vehicles_img_delete);
        }
    }

    //Interaction

    public void setData(List<Vehicle> vehicles) {
        vehicleList = vehicles;
        notifyDataSetChanged();
    }

    public interface onVehiclesAdapterInteractionListener {
        void onClickPicture(Vehicle vehicle);
        void onClickEdit(Vehicle vehicle);
        void onClickDelete(Vehicle vehicle);
    }


}
