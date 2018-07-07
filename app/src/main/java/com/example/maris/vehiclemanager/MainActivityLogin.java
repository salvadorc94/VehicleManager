package com.example.maris.vehiclemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;

public class MainActivityLogin extends AppCompatActivity {

    Button entrar;
    public static final int PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int PERMISSIONS_REQUEST_WRITE = 101;
    public static final int PERMISSIONS_REQUEST_READ = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        checkPermissions();
        entrar = findViewById(R.id.btnentrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivityMenu.class);
                startActivity(intent);
            }
        });

    }
    private void checkPermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }

        int permissionCheck2 =  ContextCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para escribir!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE);
        } else {
            Log.i("Mensaje", "Tienes permiso para escribir.");
        }

        int permissionCheck3  = ContextCompat.checkSelfPermission(
                this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ);
        } else {
            Log.i("Mensaje", "Tienes permiso para leer.");
        }
    }
}
