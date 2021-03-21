package com.example.entrega1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                Intent i = new Intent(getBaseContext(),ListadoTareas.class);
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

                SQLiteDatabase bd = miBD.getInstance(getBaseContext()).getWritableDatabase();

                String[] campos = new String[] {"Fecha"};
                String[] argumentos = new String[] {input.getText().toString()};
                //SE EJECUTA LA SENTENCIA SQL
                Cursor c = bd.query("Tareas",campos,"Titulo=?",argumentos,null,null,null);

                if (c.moveToNext()){
                    //SE MUESTRA UN TOAST SI YA EXISTE EL TITULO
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getBaseContext(), "Ya existe una tarea con ese título.", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                    aviso.show();
                }
                else if (esMasAntiguaQueActual(inputFecha.getText().toString())){
                    //SE MUESTRA UN TOAST SI LA FECHA NO ES VALIDA
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getBaseContext(), "La fecha introducida es más antigua que la actual.", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                    aviso.show();
                }
                else {
                    // NOTIFICACIONES LOCALES
                    NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "Canal1");
                    elBuilder.setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("Tarea añadida.")
                            .setVibrate(new long[] {0, 500})
                            .setAutoCancel(true);
                    elBuilder.setContentText(input.getText().toString() + " para el " + inputFecha.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel elCanal = new NotificationChannel("Canal1", "CanalMain",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        elCanal.setDescription("Canal Principal");
                        elCanal.setVibrationPattern(new long[] {0, 500});
                        elCanal.enableVibration(true);
                        elManager.createNotificationChannel(elCanal);
                    }

                    elManager.notify(1, elBuilder.build());

                    ContentValues insert = new ContentValues();
                    insert.put("Titulo", input.getText().toString());
                    insert.put("Fecha", inputFecha.getText().toString());
                    insert.put("Cuerpo", inputC.getText().toString());
                    //SE EJECUTA LA SENTENCIA SQL
                    bd.insert("Tareas", null, insert);

                    Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private boolean esMasAntiguaQueActual(String fecha) { //METODO QUE COMPRUEBA SI LA FECHA INTRODUCIDA ES MÁS ANTIGUA QUE LA ACTUAL
        String[] x = fecha.split("-");
        Calendar calendar = Calendar.getInstance();
        return parseInt(x[0])<calendar.get(Calendar.DAY_OF_MONTH) || parseInt(x[1])<calendar.get(Calendar.MONTH)+1 || parseInt(x[2])<calendar.get(Calendar.YEAR);
    }

    private void showDatePickerDialog(){//METODO QUE CREA EL DIALOGO DEL CALENDARIO
        ClaseDialogoDatePicker dialogo = new ClaseDialogoDatePicker(inputFecha);
        dialogo.show(getSupportFragmentManager(), "datePicker");
    }
}