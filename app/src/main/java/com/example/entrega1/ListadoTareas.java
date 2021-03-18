package com.example.entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoTareas extends AppCompatActivity {

    ArrayList<String> titulos = new ArrayList<String>();
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayAdapter eladaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tareas);

        ListView lista = findViewById(R.id.lista);
        Button btnAdd = findViewById(R.id.btnAdd2);
        Button btnMostrarHoy = findViewById(R.id.btnMostrarHoy);
        Button btnBuscarDia = findViewById(R.id.btnBuscarDia);

        eladaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,android.R.id.text1,titulos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View vista= super.getView(position, convertView, parent);
                TextView lineaprincipal=(TextView) vista.findViewById(android.R.id.text1);
                TextView lineasecundaria= vista.findViewById(android.R.id.text2);
                lineaprincipal.setText(titulos.get(position));
                lineasecundaria.setText(fechas.get(position));
                return vista;
            }
        };
        lista.setAdapter(eladaptador);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AddTarea.class);
                startActivityForResult(i,666);
            }
        });

//        int[] personajes={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
//        String[] nombres={"Bart Simpson","Edna Krabappel","Homer Simpson","Lisa Simpson","Seymour Skinner"};
//        double[] valoracion={3.2,2.4,4.6,4.9,3.0};
//        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),nombres,personajes,valoracion);
//        lista.setAdapter(eladap);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == RESULT_OK) {
            String requiredValue = data.getStringExtra("nuevaTask");
            String requiredValue2 = data.getStringExtra("fecha");
            titulos.add(requiredValue);
            fechas.add(requiredValue2);
            eladaptador.notifyDataSetChanged();
        }
    }
}