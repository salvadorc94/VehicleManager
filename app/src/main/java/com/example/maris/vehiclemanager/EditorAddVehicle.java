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

               // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditorAddVehicle.this);*/
            // Configura el titulo.
                // builder.setTitle(R.string.select_option);

                /*alertDialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
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


                String[] opciones = getResources().getStringArray(R.array.selection_gallery_or_camera);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item, opciones);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditorAddVehicle.this);
                alertDialogBuilder.setTitle(R.string.select_option);

                alertDialogBuilder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            // Opción Gallery
                            OpenGallery();

                        } else {
                            // Opción camara
                            OpenCamara();
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
        /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(gallery, TAKE_PICTURE_VEHICLE);
        gallery.setType("image/*");
        startActivityForResult(
                Intent.createChooser(gallery, "Select a picture"),
                TAKE_PICTURE_VEHICLE);*/

        /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, TAKE_PICTURE_VEHICLE);*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select option..."),CODE_SELECTED_GALLERY);

    }

    private void OpenCamara() {

        /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile  = timeStamp+".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE_VEHICLE);*/


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

        /*protected void onActivityResult(int requestCode, int resultCode,
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

                        imageUri = imageReturnedIntent.getData();
                        img_vehicle.setImageURI(imageUri);

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {

                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            CircleImageView IMAGE_VEHICLE = findViewById(R.id.photo_car_add_edit_v);
                            IMAGE_VEHICLE.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }*/


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



            /*case TAKE_PICTURE_VEHICLE:

                    if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE_VEHICLE) {

                        Uri selectedImage = imageUri;
                        getContentResolver().notifyChange(selectedImage,null);
                        ContentResolver cr = getContentResolver();
                        Bitmap bitmap;
                        InputStream imageStream = null;
                        try{
                            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,selectedImage);
                            img_vehicle.setImageBitmap(bitmap);
                            Toast.makeText(this,selectedImage.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("PATH",selectedImage.toString());

                        }catch (Exception e){
                            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                        }

                        // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                        CircleImageView IMAGE_VEHICLE = findViewById(R.id.photo_car_add_edit_v);
                        IMAGE_VEHICLE.setImageBitmap(bmp);

                    }
                    break;*/

