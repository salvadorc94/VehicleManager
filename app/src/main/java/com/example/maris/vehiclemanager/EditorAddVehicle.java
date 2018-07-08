package com.example.maris.vehiclemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import de.hdodenhof.circleimageview.CircleImageView;

public class EditorAddVehicle extends AppCompatActivity {

    private CircleImageView img_vehicle;
    FloatingActionButton btn_camara;
    private EditText edit_name_vehicle, edit_model_vehicle, edit_year_vehicle, edit_odometer_vehicle,
            edit_carplate_vehicle, edit_gasoline_vehicle, edit_brand_vehicle;
    private Uri imageUri;
    private Button btn_accept_vehicle;
    private Vehicle vehicle = null;

    //public static final int TAKE_PICTURE_VEHICLE = 100;
    public static final String VEHICLE = "carro";

    private final String FILE_IMAGE = "VehicleManager/"; //Directorio principal
    private final String ROUTE_IMAGE = FILE_IMAGE+"Photos"; //Carpeta donde se guardan las fotos
    private final String PRINCIPAL_FILE = FILE_IMAGE + ROUTE_IMAGE; //Ruta de carpeta de directorio donde esta almacenada la fotografia

    final int CODE_SELECTED_GALLERY = 10;
    final int CODE_SELECTED_CAMERA = 20;
    private AppViewModel viewModel;

    String path; //Almacena la ruta de la imagen
    File fileImg_vehicle;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_add_vehicle);

        img_vehicle = findViewById(R.id.photo_car_add_edit_v);
        btn_camara = findViewById(R.id.fab_add_edit_v);
        edit_name_vehicle = findViewById(R.id.id_name_add_edit_v);
        edit_model_vehicle = findViewById(R.id.id_model_add_edit_v);
        edit_brand_vehicle = findViewById(R.id.id_marca_add_edit_v);
        edit_year_vehicle = findViewById(R.id.id_year_add_edit_v);
        edit_odometer_vehicle = findViewById(R.id.id_odom_add_edit_v);
        edit_carplate_vehicle = findViewById(R.id.id_placa_add_edit_v);
        edit_gasoline_vehicle = findViewById(R.id.id_gesoline_add_edit_v);
        btn_accept_vehicle = findViewById(R.id.btn_accept_add_edit_v);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        Intent intent = getIntent();
        //vehicle = intent.hasExtra(VEHICLE) ? (Vehicle) intent.getParcelableExtra(VEHICLE) : (new Vehicle());
        if(intent.hasExtra(VEHICLE)){
            vehicle = intent.getParcelableExtra(VEHICLE);
            Log.d("SALDEBUG","si trae intent");
        }else{
            vehicle = null;
        }

        if(vehicle != null){
            Log.d("SALDEBUG","lleno datos");
        //img_vehicle TODO:MOSTRAR IMG CAMARA
            edit_name_vehicle.setText(vehicle.getName());
            edit_model_vehicle.setText(vehicle.getModel());
            edit_brand_vehicle.setText(vehicle.getBrand());
            edit_year_vehicle.setText(String.valueOf(vehicle.getYear()));
            edit_odometer_vehicle.setText(String.valueOf(vehicle.getOdometer()));
            edit_carplate_vehicle.setText(vehicle.getPlate());
            edit_gasoline_vehicle.setText(vehicle.getGasoline());
        }

        btn_camara.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String[] opciones = getResources().getStringArray(R.array.selection_gallery_or_camera);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item, opciones);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditorAddVehicle.this);
                alertDialogBuilder.setTitle(R.string.select_option);

                alertDialogBuilder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            //Gallery
                            OpenGallery();

                        } else {
                            //Camera
                            OpenCamera();
                        }


                    }
                }).create().show();

            }
        });

        btn_accept_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edit_name_vehicle.getText().toString().isEmpty() || edit_carplate_vehicle.getText().toString().isEmpty() ||
                        edit_odometer_vehicle.getText().toString().isEmpty() || edit_year_vehicle.getText().toString().isEmpty())) {

                    if(vehicle != null){
                        Log.d("SALDEBUG","meto actual");
                        saveDataVehicle();
                        viewModel.insertOrUpdateVehicles(vehicle).subscribe();
                    }else{
                        Log.d("SALDEBUG","meto nuevo");
                        viewModel.insertOrUpdateVehicles(savenewDataVehicle()).subscribe();
                    }

                Intent intent = new Intent(getApplicationContext(), MainActivityMenu.class);
                Toast.makeText(getApplicationContext(), R.string.Successful_Event, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.toast_val_addvehicle, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveDataVehicle() {
        vehicle.setName(edit_name_vehicle.getText().toString());
        vehicle.setBrand(edit_brand_vehicle.getText().toString());
        vehicle.setModel(edit_model_vehicle.getText().toString());
        vehicle.setYear(Integer.parseInt(edit_year_vehicle.getText().toString()));
        vehicle.setOdometer(Long.parseLong(edit_odometer_vehicle.getText().toString()));
        vehicle.setPlate(edit_carplate_vehicle.getText().toString());
        vehicle.setGasoline(edit_gasoline_vehicle.getText().toString());
        vehicle.setCarPic(path);
    }

    public Vehicle savenewDataVehicle() {
        Vehicle v = new Vehicle();
        v.setName(edit_name_vehicle.getText().toString());
        v.setBrand(edit_brand_vehicle.getText().toString());
        v.setModel(edit_model_vehicle.getText().toString());
        v.setYear(Integer.parseInt(edit_year_vehicle.getText().toString()));
        v.setOdometer(Long.parseLong(edit_odometer_vehicle.getText().toString()));
        v.setPlate(edit_carplate_vehicle.getText().toString());
        v.setGasoline(edit_gasoline_vehicle.getText().toString());
        v.setCarPic(path);
        return v;
    }

    private void OpenGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select option..."),CODE_SELECTED_GALLERY);

    }

    private void OpenCamera() {

        File myFile = new File(Environment.getExternalStorageDirectory(),PRINCIPAL_FILE);
        Boolean iscreate=myFile.exists();
        String name_img_vehicle = "";

        if(iscreate==false){
            iscreate=myFile.mkdirs();
        }

        if (iscreate==true) {

            //Captura fecha y hora en la que se inicia el proceso
            Long consecutive = System.currentTimeMillis() / 1000;

            name_img_vehicle = consecutive.toString() + ".jpg";


            //indicamos la ruta del almacenamiento
            path = Environment.getExternalStorageDirectory() + File.separator + PRINCIPAL_FILE + File.separator + name_img_vehicle;

            //Se construye el archivo en el path
            fileImg_vehicle = new File(path);

            //Se activa el lanzamiento de la camara
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImg_vehicle));
            startActivityForResult(intent, CODE_SELECTED_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){

                case CODE_SELECTED_GALLERY:
                    Uri  mypath = data.getData();
                    img_vehicle.setImageURI(mypath);
                    break;

                case CODE_SELECTED_CAMERA:
                    //
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("storage path","Path: "+path);
                        }
                    });

                    bitmap = BitmapFactory.decodeFile(path);
                    img_vehicle.setImageBitmap(bitmap);
                    break;

            }

        }
    }
}
