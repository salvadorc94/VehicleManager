package com.example.maris.vehiclemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.io.FileNotFoundException;
import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;

public class EditorAddVehicle extends AppCompatActivity {

    private CircleImageView img_vehicle;
    FloatingActionButton btn_camara;
    private EditText edit_name_vehicle, edit_model_vehicle, edit_year_vehicle, edit_odometer_vehicle, edit_carplate_vehicle, edit_gasoline_vehicle;
    private Uri imageUri;
    private Button accept;
    private Vehicle vehicle;

    public static final int TAKE_PICTURE_VEHICLE = 100;
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
        edit_gasoline_vehicle  = findViewById(R.id.id_gesoline_add_edit_v);
        accept = findViewById(R.id.btn_accept_add_edit_v);

        Intent intent = getIntent();
        vehicle=  intent.hasExtra(EXTRA_VEHICLE)?(Vehicle)intent.getParcelableExtra(EXTRA_VEHICLE):(new Vehicle()) ;

        btn_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String nameFile  = timeStamp+".jpg";
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PICTURE_VEHICLE);*/

                //OpenGalery(view);


                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"),
                        TAKE_PICTURE_VEHICLE);

               onButtonClicked(view);*/



                String[] opciones = getResources().getStringArray(R.array.selection_gallery_or_camara);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditorAddVehicle.this);
// Configura el titulo.
                /*alertDialogBuilder.setTitle(R.string.select_option).setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
// Configura el mensaje.
                        .setMessage("Hola Alex, ¿aceptas la opción?")
                        .setCancelable(false)
                        .setPositiveButton("Aceppt",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Si la respuesta es afirmativa aquí agrega tu función a realizar.
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();*/

                alertDialogBuilder.setTitle(R.string.select_option).setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0) {
                            OpenGallery();

                        }


            }
        })
                        .setCancelable(false)
                        .setPositiveButton("Aceppt",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Si la respuesta es afirmativa aquí agrega tu función a realizar.
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();

        edit_name_vehicle.setText(vehicle.getName());
        edit_model_vehicle.setText(vehicle.getModel());
        /*edit_year_vehicle.setText(vehicle.getYear());
        edit_odometer_vehicle.setText((int) vehicle.getOdometer());
        edit_carplate_vehicle.setText(vehicle.getPlate());
        edit_gasoline_vehicle.setText(vehicle.getGasoline());*/

                }});}

    public void saveDataVehicle(){

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

   /* public void OpenGalery(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                TAKE_PICTURE_VEHICLE);
    }*/

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case TAKE_PICTURE_VEHICLE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == TAKE_PICTURE_VEHICLE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            /*try {

                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }*/

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            CircleImageView IMAGE_VEHICLE = findViewById(R.id.photo_car_add_edit_v);
                            IMAGE_VEHICLE.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }


    private void OpenGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, TAKE_PICTURE_VEHICLE);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == TAKE_PICTURE_VEHICLE){
            imageUri = data.getData();
            img_vehicle.setImageURI(imageUri);
        }
    }*/



}
