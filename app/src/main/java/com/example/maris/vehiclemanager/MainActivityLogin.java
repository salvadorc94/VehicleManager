package com.example.maris.vehiclemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityLogin extends AppCompatActivity {

    EditText username;
    EditText password;
    Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        entrar = findViewById(R.id.btnentrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!username.equals("") && !password.equals("")){

                Intent intent = new Intent(getApplicationContext(),MainActivityMenu.class);
                startActivity(intent);

                }
                else {

                    Toast.makeText(getApplicationContext(),"Complete los campos",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
