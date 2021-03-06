package com.example.entrega1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.Calendar;

public class ListadoTareas extends AppCompatActivity {

    ArrayList<String> titulos = new ArrayList<String>();
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayAdapter eladaptador;
    TextView guardarFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tareas);

        ImageView avatar = findViewById(R.id.avatar2);
        ListView lista = findViewById(R.id.lista);
        Button btnAdd = findViewById(R.id.btnAdd2);
        Button btnMostrarHoy = findViewById(R.id.btnMostrarHoy);
        Button btnBuscarDia = findViewById(R.id.btnBuscarDia);
        guardarFecha = findViewById(R.id.guardarFecha);

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
//        String[] campos = new String[]{"Titulo", "Fecha"};
//        String[] argumentos = new String[]{};
////SE EJECUTA LA SENTENCIA SQL
//        Cursor c = bd.query("Tareas", campos, "", argumentos, null, null, null);
//
//        while (c.moveToNext()) {//POR CADA RESULTADO SE A??ADEN A LOS ARRAYS DE TITULOS Y FECHAS PARA MOSTRARLOS EN EL LISTADO
//            titulos.add(c.getString(0));
//            fechas.add((c.getString(1)));
//        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), UnaTarea.class);
                i.putExtra("titulo", titulos.get(position));
                startActivity(i);
                finish();
            }
        });

        btnMostrarHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TareasDeUnDia.class);
                Calendar calendar = Calendar.getInstance();
                int mes = calendar.get(Calendar.MONTH) + 1;
                i.putExtra("fecha", calendar.get(Calendar.DAY_OF_MONTH) + "-" + mes + "-" + calendar.get(Calendar.YEAR));
                startActivity(i);
                finish();
            }
        });

        btnBuscarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaseDialogoDatePicker2 dialogo = new ClaseDialogoDatePicker2(guardarFecha, getBaseContext());
                dialogo.show(getSupportFragmentManager(), "datePicker");
            }
        });

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddTarea.class);
                startActivityForResult(i, 666);
                finish();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Perfil.class);
                startActivity(i);
                finish();
            }
        });

    }
}