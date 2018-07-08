package com.example.maris.vehiclemanager;

import android.app.Activity;
import android.app.AlertDialog;
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
    private EditText edit_name_vehicle, edit_model_vehicle, edit_year_vehicle, edit_odometer_vehicle, edit_carplate_vehicle, edit_gasoline_vehicle;
    private Uri imageUri;
    private Button btn_accept_vehicle;
    private Vehicle vehicle;

    //public static final int TAKE_PICTURE_VEHICLE = 100;
    public static final String EXTRA_VEHICLE = "EXTRA_VEHICLE";

    private final String FILE_IMAGE = "VehicleManager/";
    private final String ROUTE_IMAGE = FILE_IMAGE+"Photos";

    final int CODE_SELECTED_GALLERY = 10;
    final int CODE_SELECTED_CAMERA = 20;

    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_add_vehicle);

        img_vehicle = findViewById(R.id.photo_car_add_edit_v);
        btn_camara = findViewById(R.id.fab_add_edit_v);
        edit_name_vehicle = findViewById(R.id.id_name_add_edit_v);
        edit_model_vehicle = findViewById(R.id.id_model_add_edit_v);
        edit_year_vehicle = findViewById(R.id.id_year_add_edit_v);
        edit_odometer_vehicle = findViewById(R.id.id_odom_add_edit_v);
        edit_carplate_vehicle = findViewById(R.id.id_placa_add_edit_v);
        edit_gasoline_vehicle = findViewById(R.id.id_gesoline_add_edit_v);
        btn_accept_vehicle = findViewById(R.id.btn_accept_add_edit_v);

        Intent intent = getIntent();
        vehicle = intent.hasExtra(EXTRA_VEHICLE) ? (Vehicle) intent.getParcelableExtra(EXTRA_VEHICLE) : (new Vehicle());

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
                Intent intent = new Intent(getApplicationContext(),MainActivityMenu.class);
                Toast.makeText(getApplicationContext(),R.string.Successful_Event,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


    public void saveDataVehicle() {

        Vehicle v = new Vehicle();
        v.setName(edit_name_vehicle.getText().toString());
        v.setModel(edit_model_vehicle.getText().toString());
        v.setYear(Integer.parseInt(edit_year_vehicle.getText().toString()));
        v.setOdometer(Long.parseLong(edit_odometer_vehicle.getText().toString()));
        v.setPlate(edit_carplate_vehicle.getText().toString());
        v.setGasoline(edit_gasoline_vehicle.getText().toString());

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_VEHICLE, v);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    private void OpenGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select option..."),CODE_SELECTED_GALLERY);

    }

    private void OpenCamera() {

        File fileimg = new File(Environment.getExternalStorageDirectory(),ROUTE_IMAGE);
        Boolean iscreate=fileimg.exists();
        String name_img_vehicle = "";

        if(iscreate==false){
            iscreate=fileimg.mkdirs();
        }

        if (iscreate==true){
            name_img_vehicle = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+ROUTE_IMAGE+File.separator+name_img_vehicle;

        File img_vehicle = new File(path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(img_vehicle));
        startActivityForResult(intent,CODE_SELECTED_CAMERA);

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
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("storage path","Path: "+path);
                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    img_vehicle.setImageBitmap(bitmap);
                    break;

            }

        }
    }
}
