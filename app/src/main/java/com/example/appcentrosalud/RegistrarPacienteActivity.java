package com.example.appcentrosalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistrarPacienteActivity extends AppCompatActivity {

    TextView lblFechaNaci;
    EditText txtNombres, txtApellidoPaterno, txtApellidoMaterno, txtCelular,txtDireccion,txtCorreo, txtFechaNaci;
    Button btnRegistrar, btnVolver;
    String  nombres, apellidoPaterno, apellidoMaterno, celular, direccion, correo, fechaNaci;
    DatePickerDialog.OnDateSetListener setListener;

    FirebaseAuth auth;


    DatabaseReference bdcentrosalud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtNombres = findViewById(R.id.txtNombre);
        txtApellidoPaterno = findViewById(R.id.txtApellidoPaterno);
        txtApellidoMaterno = findViewById(R.id.txtApellidoMaterno);
        txtCelular = findViewById(R.id.txtCelular);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCorreo = findViewById(R.id.txtCorreo);
        lblFechaNaci = findViewById(R.id.lblFechaNaci);
        txtFechaNaci = findViewById(R.id.txtFechaNaci);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        lblFechaNaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( RegistrarPacienteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                lblFechaNaci.setText(date);
            }
        };

        txtFechaNaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarPacienteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        txtFechaNaci.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();;
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombres = txtNombres.getText().toString().trim();
                apellidoPaterno = txtApellidoPaterno.getText().toString().trim();
                apellidoMaterno =txtApellidoMaterno.getText().toString().trim();
                celular = txtCelular.getText().toString().trim();
                direccion = txtDireccion.getText().toString().trim();
                correo = txtCorreo.getText().toString().trim();
                fechaNaci = txtFechaNaci.getText().toString().trim();


                registrarusuario();

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrarPacienteActivity.this , PanelActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void registrarusuario(){

        String id = UUID.randomUUID().toString();



        Map<String,Object> map = new HashMap<>();
        map.put("nombres", nombres);
        map.put("apellidoPaterno", apellidoPaterno);
        map.put("apellidoMaterno", apellidoMaterno);
        map.put("celular", celular);
        map.put("direccion", direccion);
        map.put("correo", correo);
        map.put("fechaNacimiento", fechaNaci);


        bdcentrosalud.child("pacientes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){
                    Bundle extras = new Bundle();
                    extras.putString("id", id);
                    Intent i = new Intent(RegistrarPacienteActivity.this, TratamientoTuberculosisAnteriorActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(RegistrarPacienteActivity.this, "No se guardaron los datos del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(RegistrarPacienteActivity.this, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }





    public  void limpiarCajasTexto(){
        txtNombres.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtCelular.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtFechaNaci.setText("");
    }
}