package com.example.entrega1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class TareasDeUnDia extends AppCompatActivity {

    ArrayList<String> titulos = new ArrayList<String>();
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayAdapter eladaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_de_un_dia);

        TextView titulo = findViewById(R.id.TituloFechaSeleccionada);
        ListView lista = findViewById(R.id.lista2);
        Button btnVolver = findViewById(R.id.btnVolver3);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListadoTareas.class);
                startActivity(i);
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        String valor = "";
        if (extras != null) {
            valor = extras.getString("fecha");
        }

        titulo.setText("Tareas para el " + valor);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_listadoTotal.class).build();
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
//        String[] campos = new String[] {"Titulo","Fecha"};
//        String[] argumentos = new String[] {};
////SE EJECUTA LA SENTENCIA SQL
//        Cursor c = bd.query("Tareas",campos,"",argumentos,null,null,null);
//        Calendar calendar = Calendar.getInstance();
//        int mes = calendar.get(Calendar.MONTH)+1;
//
//        while (c.moveToNext()){//COGE SOLO LAS TAREAS DE LA FECHA ACTUAL
//            if(c.getString(1)==calendar.get(Calendar.DAY_OF_MONTH)+"-"+ mes +"-"+calendar.get(Calendar.YEAR)) {
//                titulos.add(c.getString(0));
//                fechas.add(c.getString(1));
//                eladaptador.notifyDataSetChanged();
//            }
//        }

        eladaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, titulos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View vista = super.getView(position, convertView, parent);
                TextView lineaprincipal = (TextView) vista.findViewById(android.R.id.text1);
                TextView lineasecundaria = vista.findViewById(android.R.id.text2);
                lineaprincipal.setText(titulos.get(position));
                lineasecundaria.setText(fechas.get(position));
                return vista;
            }
        };
        lista.setAdapter(eladaptador);

    }
}