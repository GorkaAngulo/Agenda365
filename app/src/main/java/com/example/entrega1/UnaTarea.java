package com.example.entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
            valor= extras.getString("titulo");
        }

        SQLiteDatabase bd = miBD.getInstance(getBaseContext()).getWritableDatabase();
        String[] campos = new String[] {"Fecha","Cuerpo"};
        String[] argumentos = new String[] {valor};

        Cursor c = bd.query("Tareas",campos,"Titulo=?",argumentos,null,null,null);

        while (c.moveToNext()){
            titulo.setText(valor);
            fecha.setText(c.getString(0));
            cuerpo.setText(c.getString(1));
        }

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
                String[] argumentos = new String[] {valor};
                bd.delete("Tareas", "Titulo=?", argumentos);
                Intent i = new Intent(getBaseContext(),ListadoTareas.class);
                startActivity(i);
                finish();
            }
        });

        btnVovler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),ListadoTareas.class);
                startActivity(i);
                finish();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esMasAntiguaQueActual(fecha.getText().toString())){
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getBaseContext(), "La fecha introducida es más antigua que la actual.", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                    aviso.show();
                }
                else {
                    ContentValues modificacion = new ContentValues();
                    modificacion.put("Fecha", fecha.getText().toString());
                    modificacion.put("Cuerpo", cuerpo.getText().toString());
                    String[] argumentos = new String[]{valor};
                    bd.update("Tareas", modificacion, "Titulo=?", argumentos);
                    Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private boolean esMasAntiguaQueActual(String fecha) {
        String[] x = fecha.split("-");
        Calendar calendar = Calendar.getInstance();
        return parseInt(x[0])<calendar.get(Calendar.DAY_OF_MONTH) || parseInt(x[1])<calendar.get(Calendar.MONTH)+1 || parseInt(x[2])<calendar.get(Calendar.YEAR);
    }
}