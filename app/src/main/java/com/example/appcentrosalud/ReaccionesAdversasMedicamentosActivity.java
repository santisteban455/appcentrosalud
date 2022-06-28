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

public class ReaccionesAdversasMedicamentosActivity extends AppCompatActivity {

    EditText txtFechaNotificacion,txtTipoRAM,txtMedicamentoSospechoso,txtFechaInicio,txtFechaFinal,txtDisposicionEvolucion,txtRadiologia,txtConclusionesRecomendaciones;
    Button btnRegistrar, btnVolver;
    String fechaNotificacion, tipoRAM, medicamentoSospechoso, fechaInicio,fechaFinal,disposicionEvolucion,radiologia,conclusionrecomendacion;

    FirebaseAuth auth;
    DatabaseReference bdcentrosalud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reacciones_adversas_medicamentos);
        auth = FirebaseAuth.getInstance();
        bdcentrosalud = FirebaseDatabase.getInstance().getReference();

        txtFechaNotificacion = findViewById(R.id.txtFechaNotificacion);
        txtTipoRAM = findViewById(R.id.txtTipoRAM);
        txtMedicamentoSospechoso = findViewById(R.id.txtMedicamentoSospechoso);
        txtFechaInicio = findViewById(R.id.txtFechaInicio);
        txtFechaFinal = findViewById(R.id.txtFechaFinal);
        txtDisposicionEvolucion = findViewById(R.id.txtDisposicionEvolucion);
        txtRadiologia = findViewById(R.id.txtRadiologia);
        txtConclusionesRecomendaciones = findViewById(R.id.txtConclusionesRecomendaciones);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fechaNotificacion = txtFechaNotificacion.getText().toString().trim();
                tipoRAM = txtTipoRAM.getText().toString().trim();
                medicamentoSospechoso = txtMedicamentoSospechoso.getText().toString().trim();
                fechaInicio = txtFechaInicio.getText().toString().trim();
                fechaFinal = txtFechaFinal.getText().toString().trim();
                disposicionEvolucion = txtDisposicionEvolucion.getText().toString().trim();
                radiologia = txtRadiologia.getText().toString().trim();
                conclusionrecomendacion = txtConclusionesRecomendaciones.getText().toString().trim();


                registrarReaccionesAdversasMedicamentos();

            }
        });
    }

    public void registrarReaccionesAdversasMedicamentos(){

        String valor = getIntent().getExtras().getString("id");

        Map<String,Object> map = new HashMap<>();

        map.put("fechaNotificacion", fechaNotificacion);
        map.put("tipoRAM", tipoRAM);
        map.put("medicamentoSospechoso", medicamentoSospechoso);
        map.put("fechaInicio", fechaInicio);
        map.put("fechaFinal", fechaFinal);
        map.put("disposicionEvolucion", disposicionEvolucion);
        map.put("radiologia", radiologia);
        map.put("conclusionrecomendacion", conclusionrecomendacion);


        bdcentrosalud.child("esquemas").child(valor).child("reaccionesAdversasMedicamentos").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {

                if(task2.isSuccessful()){

                    Bundle extras = new Bundle();
                    extras.putString("id", valor);
                    Intent i = new Intent(ReaccionesAdversasMedicamentosActivity.this, PanelActivity.class);
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(ReaccionesAdversasMedicamentosActivity.this, "No se guardaron los datos de la Reacciones Adversas Medicamento del paciente en la base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Toast.makeText(ReaccionesAdversasMedicamentosActivity.this, "Reacciones Adversas Medicamentos del Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

    }
}