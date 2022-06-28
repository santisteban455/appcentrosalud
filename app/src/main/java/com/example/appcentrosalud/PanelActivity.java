package com.example.appcentrosalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PanelActivity extends AppCompatActivity {

    TextView txtNombre;
    Button btnPaciente,btnCerrarSesion, btnControl;
    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnControl = findViewById(R.id.btnControl);
        btnPaciente = findViewById(R.id.btnPaciente);
        mostrarDatos();

        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PanelActivity.this , RegistrarPacienteActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cerrarSesion();
            }
        });



    }

    public void mostrarDatos(){

        String id = auth.getCurrentUser().getUid();

        bdcentrosalud.child("usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String apellido = snapshot.child("apellidos").getValue().toString();
                    txtNombre.setText(nombre + " " + apellido);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cerrarSesion(){
        auth.signOut();
        Intent intent = new Intent(PanelActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

}