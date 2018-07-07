package com.example.maris.vehiclemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditorAddVehicle extends AppCompatActivity {

    private CircleImageView img_vehicle;
    FloatingActionButton btn_camara;
    private EditText edit_name_vehicle, edit_model_vehicle, edit_year_vehicle, edit_odometer_vehicle, edit_carplate_vehicle, edit_gasoline_vehicle;
    private Uri imageUri;
    private Button accept;
    private Vehicle vehicle;

    public static final int TAKE_PICTURE_VEHICLE = 1;
    public static final String EXTRA_VEHICLE = "EXTRA_VEHICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_add_vehicle);

        img_vehicle = findViewById(R.id.photo_car_add_edit_v);
        btn_camara = findViewById(R.id.fab_add_edit_v);
        edit_name_vehicle = findViewById(R.id.id_name_add_edit_v);
        edit_model_vehicle =  findViewById(R.id.id_model_add_edit_v);
        edit_year_vehicle =  findViewById(R.id.id_year_add_edit_v);
        edit_odometer_vehicle =  findViewById(R.id.id_odom_add_edit_v);
        edit_carplate_vehicle =  findViewById(R.id.id_placa_add_edit_v);
        edit_gasoline_vehicle  = findViewById(R.id.id_gas_add_edit_v);
        accept = findViewById(R.id.btn_accept_add_edit_v);

        Intent intent = getIntent();
        vehicle=  intent.hasExtra(EXTRA_VEHICLE)?(Vehicle)intent.getParcelableExtra(EXTRA_VEHICLE):(new Vehicle()) ;
        //takePic =  findViewById(R.id.id_takePic_add_edit_exp);
        btn_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoforVehicle();
            }
        });

    }

    public void takePhotoforVehicle(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile  = timeStamp+".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE_VEHICLE);
    }



}
