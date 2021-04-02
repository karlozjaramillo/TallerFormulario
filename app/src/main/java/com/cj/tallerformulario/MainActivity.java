package com.cj.tallerformulario;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtEdad;
    private EditText txtCargo;
    private EditText txtSalario;
    private ListView lvLista;
    private Button btnAgregar;
    private Button btnListar;
    private ArrayAdapter<String> adapter;
    private ArrayList<Persona> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        txtEdad = findViewById(R.id.txtEdad);
        txtCargo = findViewById(R.id.txtCargo);
        txtSalario = findViewById(R.id.txtSalario);
        lvLista = findViewById(R.id.lvLista);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnListar = findViewById(R.id.btnListar);
        btnAgregar.setOnClickListener(this);
        btnListar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregar:
                agregarPersona();
                limpiarCampos();
                break;
            case R.id.btnListar:
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
                lvLista.setAdapter(adapter);
                break;
        }
    }

    private void agregarPersona() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String email = txtEmail.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());
        String cargo = txtCargo.getText().toString();
        double salario = Double.parseDouble(txtSalario.getText().toString());

        Persona persona = new Persona(nombre, apellido, email, edad, salario, cargo);

        list.add(persona);
    }

    private void limpiarCampos() {
        txtNombre.getText().clear();
        txtApellido.getText().clear();
        txtEmail.getText().clear();
        txtEdad.getText().clear();
        txtCargo.getText().clear();
        txtSalario.getText().clear();
    }
}