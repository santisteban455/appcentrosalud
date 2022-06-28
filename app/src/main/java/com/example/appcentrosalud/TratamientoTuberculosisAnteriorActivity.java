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

public class TratamientoTuberculosisAnteriorActivity extends AppCompatActivity {


    EditText txtAnio, txtLugar, txtEsquema, txtEvolucion, txtCondicion;
    Button btnRegistrar, btnVolver;
    String anio, lugar, esquema, evolucion, condicion;

    FirebaseAuth auth;


    DatabaseReference bdcentrosalud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratamiento_tuberculosis_anterior);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtLugar = findViewById(R.id.txtLugar);
        txtAnio = findViewById(R.id.txtAnio);
        txtEsquema = findViewById(R.id.txtEsquema);
        txtEvolucion = findViewById(R.id.txtEvolucion);
        txtCondicion = findViewById(R.id.txtCondicion);
        btnRegistrar = findViewById(R.id.btnRegistrarTTA);
        btnVolver = findViewById(R.id.btnVolverTTA);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lugar = txtLugar.getText().toString().trim();
                anio = txtAnio.getText().toString().trim();
                esquema =txtEsquema.getText().toString().trim();
                evolucion = txtEvolucion.getText().toString().trim();
                condicion = txtCondicion.getText().toString().trim();


                registrartratamiento();

            }
        });
    }

    public void registrartratamiento(){

        String valor = getIntent().getExtras().getString("id");


        Map<String,Object> map = new HashMap<>();
        map.put("lugar", lugar);
        map.put("año", anio);
        map.put("esquema", esquema);
        map.put("evolucion", evolucion);
        map.put("condicion", condicion);


        bdcentrosalud.child("esquemas").child(valor).child("tratamientoAnterior").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){
                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(TratamientoTuberculosisAnteriorActivity.this, PruebaSensibilidadActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(TratamientoTuberculosisAnteriorActivity.this, "No se guardaron los datos del tratamiento anterior del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(TratamientoTuberculosisAnteriorActivity.this, "Tratamiento anterior del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }



}