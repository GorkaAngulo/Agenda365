package com.example.entrega1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class AddTarea extends AppCompatActivity {

    EditText inputFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarea);

        inputFecha = findViewById(R.id.fecha);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                startActivity(i);
                finish();
            }
        });

        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView input = findViewById(R.id.inputTitulo);
                TextView inputC = findViewById(R.id.inputCuerpo);

                gestionarConexion(input, inputC);
            }
        });
    }

    private boolean esMasAntiguaQueActual(String fecha) { //METODO QUE COMPRUEBA SI LA FECHA INTRODUCIDA ES MÁS ANTIGUA QUE LA ACTUAL
        String[] x = fecha.split("-");
        Calendar calendar = Calendar.getInstance();
        return parseInt(x[0]) < calendar.get(Calendar.DAY_OF_MONTH) || parseInt(x[1]) < calendar.get(Calendar.MONTH) + 1 || parseInt(x[2]) < calendar.get(Calendar.YEAR);
    }

    private void showDatePickerDialog() {//METODO QUE CREA EL DIALOGO DEL CALENDARIO
        ClaseDialogoDatePicker dialogo = new ClaseDialogoDatePicker(inputFecha);
        dialogo.show(getSupportFragmentManager(), "datePicker");
    }

    private void gestionarConexion(TextView input, TextView inputC) {
        Data datos = new Data.Builder().putString("titulo", input.getText().toString()).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_regTarea1.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (workInfo.getOutputData().getString("resultado").equals("true")) {
                                int tiempo = Toast.LENGTH_SHORT;
                                Toast aviso = Toast.makeText(getBaseContext(), "Ya existe una tarea con ese título.", tiempo);
                                aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                                aviso.show();
                            } else if (esMasAntiguaQueActual(inputFecha.getText().toString())) {
                                //SE MUESTRA UN TOAST SI LA FECHA NO ES VALIDA
                                int tiempo = Toast.LENGTH_SHORT;
                                Toast aviso = Toast.makeText(getBaseContext(), "La fecha introducida es más antigua que la actual.", tiempo);
                                aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                                aviso.show();
                            } else {
                                gestionarConexion2(input, inputC);
                            }
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }

    private void gestionarConexion2(TextView input, TextView inputC) {
        Data datos = new Data.Builder().putString("titulo", input.getText().toString()).
                putString("fecha", inputFecha.getText().toString()).
                putString("cuerpo", inputC.getText().toString()).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_regTarea2.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Intent i = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }
}
