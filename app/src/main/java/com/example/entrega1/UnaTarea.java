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

public class UnaTarea extends AppCompatActivity {

    EditText fecha;
    String valor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_una_tarea);

        TextView titulo = findViewById(R.id.inputTitulo2);
        TextView cuerpo = findViewById(R.id.inputCuerpo2);
        fecha = findViewById(R.id.fecha2);
        Button btnEliminar = findViewById(R.id.btnEliminar);
        Button btnModificar = findViewById(R.id.btnModificar);
        Button btnVovler = findViewById(R.id.btnVolver2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("titulo");
        }

        Data datos = new Data.Builder().putString("titulo", valor).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_unaTarea.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);

//        SQLiteDatabase bd = miBD.getInstance(getBaseContext()).getWritableDatabase();
//        String[] campos = new String[] {"Fecha","Cuerpo"};
//        String[] argumentos = new String[] {valor};
//
//        Cursor c = bd.query("Tareas",campos,"Titulo=?",argumentos,null,null,null);
//
//        while (c.moveToNext()){
//            titulo.setText(valor);
//            fecha.setText(c.getString(0));
//            cuerpo.setText(c.getString(1));
//        }

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaseDialogoDatePicker dialogo = new ClaseDialogoDatePicker(fecha);
                dialogo.show(getSupportFragmentManager(), "datePicker");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestionarConexion2(valor);
            }
        });

        btnVovler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                startActivity(i);
                finish();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esMasAntiguaQueActual(fecha.getText().toString())) {
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getBaseContext(), "La fecha introducida es más antigua que la actual.", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                    aviso.show();
                } else {
                    gestionarConexion(valor, fecha.getText().toString(), cuerpo.getText().toString());

//                    ContentValues modificacion = new ContentValues();
//                    modificacion.put("Fecha", fecha.getText().toString());
//                    modificacion.put("Cuerpo", cuerpo.getText().toString());
//                    String[] argumentos = new String[]{valor};
//                    bd.update("Tareas", modificacion, "Titulo=?", argumentos);
//                    Intent i = new Intent(getBaseContext(), ListadoTareas.class);
//                    startActivity(i);
//                    finish();
                }
            }
        });

    }

    private boolean esMasAntiguaQueActual(String fecha) {
        String[] x = fecha.split("-");
        Calendar calendar = Calendar.getInstance();
        return parseInt(x[0]) < calendar.get(Calendar.DAY_OF_MONTH) || parseInt(x[1]) < calendar.get(Calendar.MONTH) + 1 || parseInt(x[2]) < calendar.get(Calendar.YEAR);
    }

    private void gestionarConexion(String titulo, String fecha, String cuerpo) {
        Data datos = new Data.Builder().putString("titulo", titulo).
                putString("fecha", fecha).
                putString("cuerpo", cuerpo).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_modificarTarea.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }

    private void gestionarConexion2(String titulo) {
        Data datos = new Data.Builder().putString("titulo", titulo).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_borrarTarea.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }
}