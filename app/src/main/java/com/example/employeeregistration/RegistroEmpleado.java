package com.example.employeeregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroEmpleado extends AppCompatActivity {

    EditText edtNombre, edtApellido, edtDNI, edtPuesto, edtSalario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleado);

        edtNombre = findViewById(R.id.Ingreso_Nombre);
        edtApellido = findViewById(R.id.Ingreso_Apellido);
        edtDNI = findViewById(R.id.Ingreso_DNI);
        edtPuesto = findViewById(R.id.Ingreso_Puesto);
        edtSalario = findViewById(R.id.Ingreso_Salario);

    }

    public void BotonAgregarRegistro(View view) {

        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String dni = edtDNI.getText().toString();
        String puesto = edtPuesto.getText().toString();
        String salario = edtSalario.getText().toString();

        if (dni.length() != 8) {
            Toast.makeText(getApplicationContext(), "Make sure you put the correct number of digits on your DNI", Toast.LENGTH_SHORT).show();

        } else {
            if (nombre.equals("") || apellido.equals("") || puesto.equals("") || salario.equals("")) {
                Toast.makeText(getApplicationContext(), "Make sure you don't leave any fields empty", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder mensajeDeAlerta = new AlertDialog.Builder(RegistroEmpleado.this);
                mensajeDeAlerta.setMessage("Are you sure you want to confirm information?")
                        .setCancelable(true)

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ConexionALaBaseDeDatos("http://camustest.eu5.org/conexionEmployeeRegistration/agregar_registro.php");

                                System.gc();

                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alerta = mensajeDeAlerta.create();
                alerta.setTitle("");
                alerta.show();

            }
        }
    }

    private void ConexionALaBaseDeDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Employee successfully added", Toast.LENGTH_SHORT).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("nombreEmpleado", edtNombre.getText().toString());
                datos.put("apellidoEmpleado", edtApellido.getText().toString());
                datos.put("dniEmpleado", edtDNI.getText().toString());
                datos.put("puestoEmpleado", edtPuesto.getText().toString());
                datos.put("salarioEmpleado", edtSalario.getText().toString());
                return datos;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}