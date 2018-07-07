/*package com.example.maris.vehiclemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import static com.example.maris.vehiclemanager.EditorAddVehicle.TAKE_PICTURE_VEHICLE;


public class DialogOfSelection extends DialogFragment {

    public interface OnDialogListener{

        void OnPositiveButtonClicked();
        void OnNegativeButtonClicked();

    }

    public static final int TAKE_PICTURE_VEHICLE = 1;


    private OnDialogListener onDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onDialogListener = (OnDialogListener) getActivity();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] opciones = getResources().getStringArray(R.array.selection_galery_or_camara);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.select_option).setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                which = R.array.selection_galery_or_camara;
                String galery, camara;

                if (which == 0){

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(intent, "Seleccione una imagen"),
                            TAKE_PICTURE_VEHICLE);

                }



            }
        });


        return builder.create();

    }



}*/
