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

public class PruebaSensibilidadActivity extends AppCompatActivity {

    EditText txtCodigo, txtLaboratorio, txtMedicamentos, txtFecha;
    Button btnRegistrar, btnVolver;
    String codigo, laboratorio, medicamentos, fecha;

    FirebaseAuth auth;


    DatabaseReference bdcentrosalud;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_sensibilidad);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtLaboratorio = findViewById(R.id.txtLaboratorio);
        txtCodigo = findViewById(R.id.txtCodigo);
        txtMedicamentos = findViewById(R.id.txtMedicamentos);
        txtFecha = findViewById(R.id.txtFecha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                laboratorio = txtLaboratorio.getText().toString().trim();
                codigo = txtCodigo.getText().toString().trim();
                medicamentos =txtMedicamentos.getText().toString().trim();
                fecha = txtFecha.getText().toString().trim();


                registrarSENSIBILIDAD();

            }
        });

    }

    public void registrarSENSIBILIDAD(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("laboratorio", laboratorio);
        map.put("codigo", codigo);
        map.put("medicamentos", medicamentos);
        map.put("fecha", fecha);


        bdcentrosalud.child("esquemas").child(valor).child("sensibilidad").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){
                    startActivity(new Intent(PruebaSensibilidadActivity.this,PanelActivity.class));
                    finish();
                }else{
                    Toast.makeText(PruebaSensibilidadActivity.this, "No se guardaron los datos del tratamiento anterior del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(PruebaSensibilidadActivity.this, "Tratamiento anterior del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }



}