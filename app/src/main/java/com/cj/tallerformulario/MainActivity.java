package com.cj.tallerformulario;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtEdad;
    private Spinner dropdown;
    private EditText txtSalario;
    private ListView lvLista;
    private Button btnAgregar;
    private Button btnListar;
    private Button btnEdad;
    private Button btnSalario;
    private ArrayAdapter<String> adapter;
    private ArrayList<Persona> personas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        txtEdad = findViewById(R.id.txtEdad);
        dropdown = findViewById(R.id.spinner);
        txtSalario = findViewById(R.id.txtSalario);
        lvLista = findViewById(R.id.lvLista);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnListar = findViewById(R.id.btnListar);
        btnEdad = findViewById(R.id.btnEdad);
        btnSalario = findViewById(R.id.btnSalario);
        btnAgregar.setOnClickListener(this);
        btnListar.setOnClickListener(this);
        btnEdad.setOnClickListener(this);
        btnSalario.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.cargos, android.R.layout.simple_spinner_item);
        dropdown.setAdapter(adapterSpinner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregar:
                agregarPersona();

                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, personas);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnListar:
                ArrayList<String> salariosCargos = new ArrayList<>();
                String desarrollador = "Desarrollador";
                String analista = "Analista";
                String qa = "QA";
                String mensajeDesarrollador = "";
                String mensajeAnalista = "";
                String mensajeQA = "";
                for (Persona persona : personas
                ) {
                    switch (persona.getCargo()) {
                        case "Desarrollador":
                            mensajeDesarrollador = "Desarrolladores: " + contarCargo(desarrollador) + "\n" +
                                    "El salario promedio de un " + desarrollador + " es $" + salarioPromedioCargo(desarrollador);
                            break;
                        case "Analista":
                            mensajeAnalista = "Analistas: " + contarCargo(analista) + "\n" +
                                    "El salario promedio de un " + analista + " es $" + salarioPromedioCargo(analista);
                        case "QA":
                            mensajeQA = "QA's: " + contarCargo(qa) + "\n" +
                                    "El salario promedio de un " + qa + " es $" + salarioPromedioCargo(qa);
                    }
                }
                salariosCargos.add(mensajeDesarrollador);
                salariosCargos.add(mensajeAnalista);
                salariosCargos.add(mensajeQA);
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, salariosCargos);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnEdad:
                ordenarMenorMayorEdad();
                ArrayList<String> edadPersonas = new ArrayList<>();
                String joven = "La persona más joven es " + personas.get(0);
                String vieja = "La persona más vieja es " + personas.get(personas.size() - 1);
                edadPersonas.add(joven);
                edadPersonas.add(vieja);
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, edadPersonas);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnSalario:
                ordenarMenorMayorSalario();
                ArrayList<String> salarioPersonas = new ArrayList<>();
                String bajo = "El salario más bajo es para " + personas.get(0);
                String alto = "El salario más alto es para " + personas.get(personas.size() - 1);
                String salarioPromedio = "El salario promedio es $" + salarioPromedioTotal();
                salarioPersonas.add(bajo);
                salarioPersonas.add(alto);
                salarioPersonas.add(salarioPromedio);
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, salarioPersonas);
                lvLista.setAdapter(adapter);
                break;
        }
    }

    private void agregarPersona() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String email = txtEmail.getText().toString();
        String edad = txtEdad.getText().toString();
        String cargo = dropdown.getSelectedItem().toString();
        String salario = txtSalario.getText().toString();

        if (!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !cargo.isEmpty()
                && !edad.isEmpty() && !salario.isEmpty()) {
            if (!validarEmail(email)) {
                Toasty.warning(this, "El formato del correo no es válido", Toast.LENGTH_SHORT, true).show();
            } else {
                Persona persona = new Persona(nombre, apellido, email, Integer.parseInt(edad), Double.parseDouble(salario), cargo);
                personas.add(persona);
                limpiarCampos();
                Toasty.success(this, "Se ha agregado a " + nombre + " " + apellido, Toast.LENGTH_SHORT, true).show();
            }
        } else {
            Toasty.error(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT, true).show();
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void limpiarCampos() {
        txtNombre.getText().clear();
        txtApellido.getText().clear();
        txtEmail.getText().clear();
        txtEdad.getText().clear();
        dropdown.setSelection(0);
        txtSalario.getText().clear();
    }

    private void ordenarMenorMayorEdad() {
        Collections.sort(personas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return new Integer(p1.getEdad()).compareTo(new Integer(p2.getEdad()));
            }
        });
    }

    private void ordenarMenorMayorSalario() {
        Collections.sort(personas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return new Double(p1.getSalario()).compareTo(new Double(p2.getSalario()));
            }
        });
    }

    private Double salarioPromedioTotal() {
        int contador = 0;
        double salarioTotal = 0;
        double promedio;
        for (Persona persona : personas) {
            salarioTotal += persona.getSalario();
            contador++;
        }
        promedio = salarioTotal / contador;
        return promedio;
    }

    private Double salarioPromedioCargo(String cargo) {
        int contador = 0;
        double salarioTotal = 0;
        double promedio;
        for (Persona persona : personas) {
            if (persona.getCargo().equals(cargo)) {
                salarioTotal += persona.getSalario();
                contador++;
            }
        }
        promedio = salarioTotal / contador;
        return promedio;
    }

    private int contarCargo(String cargo) {
        int contador = 0;
        for (Persona persona : personas) {
            if (persona.getCargo().equals(cargo)) {
                contador++;
            }
        }
        return contador;
    }
}