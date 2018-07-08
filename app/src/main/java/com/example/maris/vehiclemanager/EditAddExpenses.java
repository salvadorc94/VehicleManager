package com.example.maris.vehiclemanager;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EditAddExpenses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText edit_exp,edit_cost,edit_odom,edit_place,edit_date;
    private Spinner spin_vehicle,spin_category;
    private Date selected_date;
    private File photo;
    private ImageView image, img_date,takePic;
    private Button save,calendar;
    private Expense expense;
    private Uri imageUri;
    private AppViewModel viewmodel;
    private List<Vehicle> list_vehicules;
    private List<Category> list_categories;
    private Vehicle selected_id_car;
    DatePickerDialog.OnDateSetListener mDataSetListener;

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
        edit_date =  findViewById(R.id.id_date_text_add_edit_exp);
        img_date =  findViewById(R.id.id_calendar_add_edit_exp);
        spin_vehicle =  findViewById(R.id.id_vechicle_spinner_add_edit_exp);
        spin_category = findViewById(R.id.id_category_add_edit_exp);
        save =  findViewById(R.id.id_save_add_edit_exp);
        calendar = findViewById(R.id.id_calendar_btn_add_edit_exp);
        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDataSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edit_date.setVisibility(View.VISIBLE);
                selected_date = new Date(year,month,dayOfMonth);
                edit_date.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };

        image = findViewById(R.id.id_photo_add_edit_exp);

        Intent intent = getIntent();
        expense =  intent.hasExtra(EXTRA_EXPENSE)?(Expense)intent.getParcelableExtra(EXTRA_EXPENSE):(new Expense()) ;

        if (expense.getIdExp() == 0) {
            Calendar c = Calendar.getInstance();
            mDataSetListener.onDateSet(null, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));

        }
        else {
            Date expDate = expense.getDate();
            mDataSetListener.onDateSet(null, expDate.getYear(), expDate.getMonth(), expDate.getDate());
        }

        viewmodel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewmodel.getAllVehicles().first(new ArrayList<>()).map((vehicles -> {
            ArrayList<String> array = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                array.add(vehicle.getName());
            }
            list_vehicules =  vehicles;
            if (!list_vehicules.isEmpty()){
                selected_id_car = list_vehicules.get(0);
                edit_odom.setText(selected_id_car.getOdometer()+"");
            }
            return array;
        })).subscribe((vehiculos, throwable) -> {

            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {

                    ArrayAdapter<String> vehicle_adapter = new ArrayAdapter<String>(EditAddExpenses.this,android.R.layout.simple_spinner_dropdown_item, vehiculos);
                    vehicle_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spin_vehicle.setAdapter(vehicle_adapter);

                }
            };
            handler.sendEmptyMessage(1);

        });


        viewmodel.getAllCategories().first(new ArrayList<>()).map((categories -> {
            ArrayList<String> arrayList = new ArrayList<>();
            for (Category category : categories){
                arrayList.add(category.getCategory());
            }
            list_categories = categories;
            return arrayList;

        })).subscribe((categorias, throwable) -> {
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {

                    ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(EditAddExpenses.this,android.R.layout.simple_spinner_dropdown_item, categorias);
                    category_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spin_category.setAdapter(category_adapter);

                }
            };
            handler.sendEmptyMessage(1);
        });


        if(expense.getIdExp() != 0){
            edit_exp.setText(expense.getExpense());
            edit_cost.setText(expense.getCost()+"");
            spin_vehicle.setOnItemSelectedListener(this);
            edit_place.setText(expense.getPlace());
            edit_date.setText(expense.getDate()+"");
            image.setImageURI(Uri.parse(expense.getReceipt()));


        }


        takePic =  findViewById(R.id.id_takePic_add_edit_exp);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePhoto();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.getDrawable() == null){
                    Toast.makeText(getApplicationContext(),"Take a Picture",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(expense.getReceipt()), "image/*");
                    startActivity(intent);
                }
            }
        });
        if(savedInstanceState!=null){
            imageUri =  Uri.parse(savedInstanceState.getString("PATH"));
            image.setImageURI(imageUri);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");

                Calendar calendar = Calendar.getInstance();
                calendar.set(selected_date.getYear(),selected_date.getMonth(),selected_date.getDate());
                ContentUris.appendId(builder,calendar.getTimeInMillis());
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (image.getDrawable()!=null){
            outState.putString("PATH",expense.getReceipt());
        }

    }

    public void takePhoto(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile  = timeStamp+".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(EditAddExpenses.this,BuildConfig.APPLICATION_ID + ".provider",photo));
        imageUri = FileProvider.getUriForFile(EditAddExpenses.this,BuildConfig.APPLICATION_ID + ".provider",photo);
        //imageUri = Uri.fromFile(photo);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        edit_odom.setText(list_vehicules.get(position).getOdometer()+"");
        selected_id_car =  list_vehicules.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void saveExpense(){
        if (validInput()){
            expense.setExpense(edit_exp.getText().toString());
            expense.setCost(Float.parseFloat(edit_cost.getText().toString()));
            expense.setIdCar(selected_id_car.getIdCar());
            selected_id_car.setOdometer(selected_id_car.getOdometer());
            expense.setPlace(edit_place.getText().toString());
            expense.setDate(selected_date);
            if(imageUri != null)expense.setReceipt(imageUri.toString()); else expense.setReceipt("");
            viewmodel.insertOrUpdateExpenses(expense).subscribe();
            viewmodel.insertOrUpdateVehicles(selected_id_car).subscribe();

            finish();

        }
        else {
            //TODO: move to resources
            Toast.makeText(this, "Could not save expense",Toast.LENGTH_LONG).show();
        }
    }
    public boolean validInput(){
        return !(edit_exp.getText().toString().isEmpty() ||
        edit_cost.getText().toString().isEmpty() ||
        edit_odom.getText().toString().isEmpty() ||
        edit_date.getText().toString().isEmpty() ||
        edit_place.getText().toString().isEmpty() ||
        selected_date == null ||
        spin_vehicle.getSelectedItem().toString().isEmpty());
    }
}
