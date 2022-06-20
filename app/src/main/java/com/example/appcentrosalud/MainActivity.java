package com.example.appcentrosalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    EditText txtCorreo, txtContrasenia;
    Button  btnSesion;
    String  correo, contrasenia;
    ProgressBar load;

    FirebaseAuth auth;

    DatabaseReference bdcentrosalud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        btnSesion = findViewById(R.id.btnSesion);
        load = findViewById(R.id.load);

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = txtCorreo.getText().toString().trim();
                contrasenia = txtContrasenia.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){

                    txtCorreo.setError("Correo invalido");
                    txtCorreo.setFocusable(true);

                }
                else if(txtContrasenia.length()<6){
                    txtContrasenia.setError("Contraseña debe tener minímo 6 carácteres");
                    txtContrasenia.setFocusable(true);

                }else{
                    iniciarsesion(correo,contrasenia);
                }
            }
        });
    }


    public void iniciarsesion(String correo, String contrasenia){

        load.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    limpiarCajasTexto();
                    load.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this , PanelActivity.class);
                    startActivity(intent);
                }else{
                    limpiarSesionErronea();
                    load.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                limpiarSesionErronea();
                load.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void limpiarSesionErronea(){

        txtContrasenia.setText("");
    }

    public  void limpiarCajasTexto(){

        txtCorreo.setText("");
        txtContrasenia.setText("");
    }
}