package com.example.employeeregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    static final long DELAYED_TIME = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent irALaSiguienteActivity = new Intent(MainActivity.this, EmpleadosVentanaPrincipal.class);
                startActivity(irALaSiguienteActivity);
                finish();
            }
        }, DELAYED_TIME);
    }
}