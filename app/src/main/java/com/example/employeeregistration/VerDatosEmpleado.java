package com.example.employeeregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerDatosEmpleado extends AppCompatActivity {

    EditText edtNombre, edtApellido, edtDNI, edtPuesto, edtSalario, buscarPorDNI;

    TextView signoDolar;

    Button guardarCambios;

    ImageButton editarRegistro, eliminarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_empleado);

        buscarPorDNI = findViewById(R.id.Buscar_Por_DNI);

        edtNombre = findViewById(R.id.Ingreso_Nombre);
        edtApellido = findViewById(R.id.Ingreso_Apellido);
        edtDNI = findViewById(R.id.Ingreso_DNI);
        edtPuesto = findViewById(R.id.Ingreso_Puesto);
        edtSalario = findViewById(R.id.Ingreso_Salario);

        signoDolar = findViewById(R.id.Signo_Dolar_2);

        guardarCambios = findViewById(R.id.Guardar_Cambios);

        editarRegistro = findViewById(R.id.Editar_Registro);
        eliminarRegistro = findViewById(R.id.Eliminar_Registro);

        edtNombre.setVisibility(View.GONE);
        edtApellido.setVisibility(View.GONE);
        edtDNI.setVisibility(View.GONE);
        edtPuesto.setVisibility(View.GONE);
        edtSalario.setVisibility(View.GONE);

        signoDolar.setVisibility(View.GONE);

        edtNombre.setFocusable(false);
        edtApellido.setFocusable(false);
        edtDNI.setFocusable(false);
        edtPuesto.setFocusable(false);
        edtSalario.setFocusable(false);

        guardarCambios.setVisibility(View.GONE);

        editarRegistro.setVisibility(View.GONE);
        eliminarRegistro.setVisibility(View.GONE);

    }

    public void BotonBuscarRegistro(View view) {
        BuscarEmpleadoPorDNI("http://camustest.eu5.org/conexionEmployeeRegistration/buscar_registro.php?dniEmpleado=" + buscarPorDNI.getText() + "");

        InputMethodManager ocultarTeclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ocultarTeclado.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public void BotonEditarRegistro(View view) {
        guardarCambios.setVisibility(View.VISIBLE);

        editarRegistro.setVisibility(View.INVISIBLE);
        eliminarRegistro.setVisibility(View.INVISIBLE);

        edtNombre.setFocusableInTouchMode(true);
        edtApellido.setFocusableInTouchMode(true);
        edtPuesto.setFocusableInTouchMode(true);
        edtSalario.setFocusableInTouchMode(true);

        edtNombre.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        edtApellido.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        edtPuesto.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        edtSalario.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);

    }

    public void BotonSaveChanges(View view) {
        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String dni = edtDNI.getText().toString();
        String puesto = edtPuesto.getText().toString();
        String salario = edtSalario.getText().toString();

        if(dni.length() != 8) {
            Toast.makeText(getApplicationContext(), "Make sure you put the correct number of digits on your DNI", Toast.LENGTH_SHORT).show();

        } else {
            if(nombre.equals("") || apellido.equals("") || puesto.equals("") || salario.equals("")) {
                Toast.makeText(getApplicationContext(), "Make sure you don't leave any fields empty", Toast.LENGTH_SHORT).show();

            } else {
                ConexionALaBaseDeDatos("http://camustest.eu5.org/conexionEmployeeRegistration/editar_registro.php");

                Toast.makeText(getApplicationContext(), "Register edited successfully", Toast.LENGTH_SHORT).show();

                System.gc();

                finish();
            }
        }
    }

    public void BotonEliminarRegistro(View view) {

        AlertDialog.Builder mensajeDeAlerta = new AlertDialog.Builder(VerDatosEmpleado.this);
        mensajeDeAlerta.setMessage("Are you sure you want to delete the register?")
                .setCancelable(true)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConexionALaBaseDeDatos("http://camustest.eu5.org/conexionEmployeeRegistration/eliminar_registro.php");

                        Toast.makeText(getApplicationContext(), "Register removed successfully", Toast.LENGTH_SHORT).show();

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

    private void BuscarEmpleadoPorDNI(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtNombre.setText(jsonObject.getString("nombreEmpleado"));
                        edtApellido.setText(jsonObject.getString("apellidoEmpleado"));
                        edtDNI.setText(jsonObject.getString("dniEmpleado"));
                        edtPuesto.setText(jsonObject.getString("puestoEmpleado"));
                        edtSalario.setText(jsonObject.getString("salarioEmpleado"));

                        edtNombre.setVisibility(View.VISIBLE);
                        edtApellido.setVisibility(View.VISIBLE);
                        edtPuesto.setVisibility(View.VISIBLE);
                        edtSalario.setVisibility(View.VISIBLE);

                        signoDolar.setVisibility(View.VISIBLE);

                        edtNombre.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        edtApellido.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        edtPuesto.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        edtSalario.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

                        editarRegistro.setVisibility(View.VISIBLE);
                        eliminarRegistro.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection error, or product not found", Toast.LENGTH_LONG).show();

                edtNombre.setVisibility(View.GONE);
                edtApellido.setVisibility(View.GONE);
                edtDNI.setVisibility(View.GONE);
                edtPuesto.setVisibility(View.GONE);
                edtSalario.setVisibility(View.GONE);

                signoDolar.setVisibility(View.GONE);

                guardarCambios.setVisibility(View.GONE);

                editarRegistro.setVisibility(View.GONE);
                eliminarRegistro.setVisibility(View.GONE);

            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void ConexionALaBaseDeDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

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