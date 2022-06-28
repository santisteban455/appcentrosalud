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

public class ExamenClinicoActivity extends AppCompatActivity {

    EditText txtTalla, txtPeso, txtFR, txtFC,txtExamenPreferencial;
    Button btnRegistrar, btnVolver;
    String talla, peso, fr, fc, examenPreferencial;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_clinico);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtTalla = findViewById(R.id.txtTalla);
        txtPeso = findViewById(R.id.txtPeso);
        txtFR = findViewById(R.id.txtFR);
        txtFC = findViewById(R.id.txtFC);
        txtExamenPreferencial = findViewById(R.id.txtExamenPreferencial);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                talla = txtTalla.getText().toString().trim();
                peso = txtPeso.getText().toString().trim();
                fr = txtFR.getText().toString().trim();
                fc = txtFC.getText().toString().trim();
                examenPreferencial = txtExamenPreferencial.getText().toString().trim();


                registrarExamenClinico();

            }
        });
    }
    public void registrarExamenClinico(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("talla", talla);
        map.put("peso", peso);
        map.put("FR", fr);
        map.put("FC", fc);
        map.put("examenPreferencial", examenPreferencial);


        bdcentrosalud.child("esquemas").child(valor).child("examenClinico").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(ExamenClinicoActivity.this, EsquemaActualActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(ExamenClinicoActivity.this, "No se guardaron los datos de la condición actual del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(ExamenClinicoActivity.this, "Condición actual del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }
}