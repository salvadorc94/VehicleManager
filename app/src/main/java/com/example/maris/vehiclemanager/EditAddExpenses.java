package com.example.maris.vehiclemanager;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Model.Database.Expense;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditAddExpenses extends AppCompatActivity {
    private EditText edit_exp,edit_cost,edit_odom,edit_place,edit_date;
    private Spinner time1,time2;
    private ImageView image;
    private Button takePic,accept;
    private Expense expense;
    private Uri imageUri;
    public static final String EXTRA_EXPENSE = "EXTRA_EXPENSE";
    public static final int TAKE_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_expenses);

        edit_exp = findViewById(R.id.id_exp_add_edit_exp);
        edit_cost = findViewById(R.id.id_cost_add_edit_exp);
        edit_odom =  findViewById(R.id.id_odom_add_edit_exp);
        edit_place =  findViewById(R.id.id_place_add_edit_exp);
        edit_date =  findViewById(R.id.id_date_add_edit_exp);
        time1 =  findViewById(R.id.id_rem1_add_edit_exp);
        time2  = findViewById(R.id.id_rem2_add_edit_exp);
        image = findViewById(R.id.id_photo_add_edit_exp);
//
//        Intent intent = getIntent();
//        expense =  intent.hasExtra(EXTRA_EXPENSE)?(Expense)intent.getParcelableExtra(EXTRA_EXPENSE):(new Expense()) ;
        takePic =  findViewById(R.id.id_takePic_add_edit_exp);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_reminder_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        time1.setAdapter(adapter);

    }
    public void takePhoto(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile  = timeStamp+".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage,null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try{
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,selectedImage);
                        image.setImageBitmap(bitmap);
                        Toast.makeText(this,selectedImage.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("PATH",selectedImage.toString());

                    }catch (Exception e){
                        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}
