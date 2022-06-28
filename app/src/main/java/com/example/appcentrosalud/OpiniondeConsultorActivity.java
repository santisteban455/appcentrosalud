package com.example.appcentrosalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class OpiniondeConsultorActivity extends AppCompatActivity {

    EditText txtDirecciondeSalud,txtRedSalud,txtMicrored,txtEstablecimientoSalud,txtMedicoTratante,txtOtros,txtFechaInicioTratamiento,txtFechaFinTratamiento,txtLugaryFecha;
    Button btnRegistrar, btnVolver;
    String direccionSalud, redSalud, microRed, establecimientoSalud,medicoTratante,otros,fechaInicio,fechaFin, lugarFecha;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinionde_consultor);
        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtDirecciondeSalud = findViewById(R.id.txtDirecciondeSalud);
        txtRedSalud = findViewById(R.id.txtRedSalud);
        txtMicrored = findViewById(R.id.txtMicrored);
        txtEstablecimientoSalud = findViewById(R.id.txtEstablecimientoSalud);
        txtMedicoTratante = findViewById(R.id.txtMedicoTratante);
        txtOtros = findViewById(R.id.txtOtros);
        txtFechaInicioTratamiento = findViewById(R.id.txtFechaInicioTratamiento);
        txtFechaFinTratamiento = findViewById(R.id.txtFechaFinTratamiento);
        txtLugaryFecha = findViewById(R.id.txtLugaryFecha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                direccionSalud = txtDirecciondeSalud.getText().toString().trim();
                redSalud = txtRedSalud.getText().toString().trim();
                microRed = txtMicrored.getText().toString().trim();
                establecimientoSalud = txtEstablecimientoSalud.getText().toString().trim();
                medicoTratante = txtMedicoTratante.getText().toString().trim();
                otros = txtOtros.getText().toString().trim();
                fechaInicio = txtFechaInicioTratamiento.getText().toString().trim();
                fechaFin = txtFechaFinTratamiento.getText().toString().trim();
                lugarFecha= txtLugaryFecha.getText().toString().trim();

                registrarOpinionConsultor();

            }
        });
    }


    public void registrarOpinionConsultor(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("direccionSalud", direccionSalud);
        map.put("redSalud", redSalud);
        map.put("microRed", microRed);
        map.put("establecimientoSalud", establecimientoSalud);
        map.put("medicoTratante", medicoTratante);
        map.put("otros", otros);
        map.put("fechaInicioTratamiento", fechaInicio);
        map.put("fechaFinTratamiento", fechaFin);
        map.put("lugarYFecha", lugarFecha);


        bdcentrosalud.child("esquemas").child(valor).child("opinionConsultor").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(OpiniondeConsultorActivity.this, CondicionIngresoActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(OpiniondeConsultorActivity.this, "No se guardaron los datos de la Opinion consultor del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(OpiniondeConsultorActivity.this, "Opinion Consultor del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }
}