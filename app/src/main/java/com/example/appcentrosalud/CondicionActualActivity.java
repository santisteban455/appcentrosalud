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

public class CondicionActualActivity extends AppCompatActivity {

    EditText txtFiebre, txtPerdidaPeso, txtPeso, txtDificultadRes, txtOtros;
    Button btnRegistrar, btnVolver;
    String fiebre, perdidaPeso, peso, dificultadRes, otros;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condicion_actual);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtFiebre = findViewById(R.id.txtFiebre);
        txtPerdidaPeso = findViewById(R.id.txtPerdidaPeso);
        txtPeso = findViewById(R.id.txtPeso);
        txtDificultadRes = findViewById(R.id.txtDificultadRes);
        txtOtros = findViewById(R.id.txtOtros);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fiebre = txtFiebre.getText().toString().trim();
                perdidaPeso = txtPerdidaPeso.getText().toString().trim();
                peso =txtPeso.getText().toString().trim();
                dificultadRes = txtDificultadRes.getText().toString().trim();
                otros = txtOtros.getText().toString().trim();


                registrarCondicionActual();

            }
        });
    }

    public void registrarCondicionActual(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("fiebre", fiebre);
        map.put("perdidaPeso", perdidaPeso);
        map.put("peso", peso);
        map.put("dificultadRespiratoria", dificultadRes);
        map.put("otros", otros);


        bdcentrosalud.child("esquemas").child(valor).child("condicionActual").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(CondicionActualActivity.this, ExamenClinicoActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(CondicionActualActivity.this, "No se guardaron los datos de la condición actual del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(CondicionActualActivity.this, "Condición actual del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }


}