package com.example.employeeregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmpleadosVentanaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados_ventana_principal);

    }

    public void IrARegistroDeEmpleado(View view) {
        Intent irALaSiguienteActivity = new Intent(EmpleadosVentanaPrincipal.this, RegistroEmpleado.class);
        startActivity(irALaSiguienteActivity);

    }

    public void IrABuscarPorDNI(View view) {
        Intent irALaSiguienteActivity = new Intent(EmpleadosVentanaPrincipal.this, VerDatosEmpleado.class);
        startActivity(irALaSiguienteActivity);

    }
}