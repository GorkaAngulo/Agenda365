package com.example.entrega1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class ClaseDialogoDatePicker2  extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    android.content.Context contexto;
    TextView txt;
    public ClaseDialogoDatePicker2(TextView pTxt, android.content.Context cnt) {
        txt = pTxt;
        contexto = cnt;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog eldialogo = new DatePickerDialog(getActivity(),this, year,month,day);
        eldialogo.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Intent i = new Intent (contexto, TareasDeUnDia.class);
                i.putExtra("fecha",txt.getText().toString());
                startActivity(i);
            }
        });
        return eldialogo;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        final String selectedDate = twoDigits(day)+"-"+twoDigits(month + 1)+"-"+year;
        txt.setText(selectedDate);


    }

    private String twoDigits(int day) {
        return (day<10) ? ("0"+day) : String.valueOf(day);
    }//METODO QUE SE ASEGURA QUE LOS DIAS Y LOS MESES TENGAN DOS DIGITOS SIEMPRE


}
