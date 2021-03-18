package com.example.entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTarea extends AppCompatActivity {

    EditText inputFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarea);

        inputFecha = findViewById(R.id.fecha);
        Button btnAgregar = findViewById(R.id.btnAgregar);

        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView input = findViewById(R.id.input);
                String task = input.getText().toString();

                Intent i = new Intent();
                i.putExtra("nuevaTask",task);
                i.putExtra("fecha",inputFecha.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void showDatePickerDialog(){
        ClaseDialogoDatePicker dialogo = new ClaseDialogoDatePicker(inputFecha);
        dialogo.show(getSupportFragmentManager(), "datePicker");
    }
}