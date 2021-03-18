package com.example.entrega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        Button btnRegis = findViewById(R.id.btnRegistrar2);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usu = findViewById(R.id.usuNuevo);
                TextView pass = findViewById(R.id.passNueva);

                SQLiteDatabase bd = miBD.getInstance(getBaseContext()).getWritableDatabase();

                ContentValues insert = new ContentValues();
                insert.put("nUsuario", usu.toString());
                insert.put("pass", pass.toString());

                bd.insert("Usuarios", null, insert);
                Intent i = new Intent (getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}