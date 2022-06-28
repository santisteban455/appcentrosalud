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

public class CondicionIngresoActivity extends AppCompatActivity {

    EditText txtCondiciondeIngreso,txtDiagnosticoIngreso,txtEsquemaIndicado,txtPlanTrabajo;
    Button btnRegistrar, btnVolver;
    String condicionIngreso, diagnosticoIngreso, esquemaIndicado, planTrabajo;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condicion_ingreso);
        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtCondiciondeIngreso = findViewById(R.id.txtCondiciondeIngreso);
        txtDiagnosticoIngreso = findViewById(R.id.txtDiagnosticoIngreso);
        txtEsquemaIndicado = findViewById(R.id.txtEsquemaIndicado);
        txtPlanTrabajo = findViewById(R.id.txtPlanTrabajo);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                condicionIngreso = txtCondiciondeIngreso.getText().toString().trim();
                diagnosticoIngreso = txtDiagnosticoIngreso.getText().toString().trim();
                esquemaIndicado = txtEsquemaIndicado.getText().toString().trim();
                planTrabajo = txtPlanTrabajo.getText().toString().trim();

                registrarCondicionIngreso();

            }
        });
    }

    public void registrarCondicionIngreso(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("condicionIngreso", condicionIngreso);
        map.put("diagnosticoIngreso", diagnosticoIngreso);
        map.put("esquemaIndicado", esquemaIndicado);
        map.put("planTrabajo", planTrabajo);


        bdcentrosalud.child("esquemas").child(valor).child("condicionIngreso").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Intent i = new Intent(CondicionIngresoActivity.this, PanelActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(CondicionIngresoActivity.this, "No se guardaron los datos de laCondicion de Ingreso del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(CondicionIngresoActivity.this, "Condicion de Ingreso del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }
}