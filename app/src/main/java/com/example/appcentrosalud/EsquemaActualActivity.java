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

public class EsquemaActualActivity extends AppCompatActivity {

    EditText txtEsquemaActual,txtFechaInicio,txtEvolucion,txtRegularidadTrat,txtEstimadadeDosisPerdidas;
    Button btnRegistrar, btnVolver;
    String esquemaActual, fechaInicio, evolucion, regularidadTratamiento,estimadadedosisperdidas;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema_actual);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtEsquemaActual = findViewById(R.id.txtEsquemaActual);
        txtFechaInicio = findViewById(R.id.txtFechaInicio);
        txtEvolucion = findViewById(R.id.txtEvolucion);
        txtRegularidadTrat = findViewById(R.id.txtRegularidadTrat);
        txtEstimadadeDosisPerdidas = findViewById(R.id.txtEstimadadeDosisPerdidas);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                esquemaActual = txtEsquemaActual.getText().toString().trim();
                fechaInicio = txtFechaInicio.getText().toString().trim();
                evolucion = txtEvolucion.getText().toString().trim();
                regularidadTratamiento = txtRegularidadTrat.getText().toString().trim();
                estimadadedosisperdidas = txtEstimadadeDosisPerdidas.getText().toString().trim();


                registrarEsquemaActual();

            }
        });
    }

    public void registrarEsquemaActual(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("esquemaActual", esquemaActual);
        map.put("fechaInicio", fechaInicio);
        map.put("evolucion", evolucion);
        map.put("regularidadTratamiento", regularidadTratamiento);
        map.put("estimadadedosisperdidas", estimadadedosisperdidas);


        bdcentrosalud.child("esquemas").child(valor).child("esquemaActual").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(EsquemaActualActivity.this, ReaccionesAdversasMedicamentosActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(EsquemaActualActivity.this, "No se guardaron los datos de la condición actual del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(EsquemaActualActivity.this, "Condición actual del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }

}