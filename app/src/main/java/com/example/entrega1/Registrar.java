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
import android.widget.TextView;
import android.widget.Toast;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        Button btnRegis = findViewById(R.id.btnRegistrar2);
        Button btnVolver = findViewById(R.id.btnVolver4);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usu = findViewById(R.id.usuNuevo);
                TextView pass = findViewById(R.id.passNueva);

                SQLiteDatabase bd = miBD.getInstance(getBaseContext()).getWritableDatabase();

                String[] campos = new String[] {"nUsuario"};
                String[] argumentos = new String[] {usu.getText().toString()};

                Cursor c = bd.query("Usuarios",campos,"nUsuario=?",argumentos,null,null,null);

                if (c.moveToNext()){
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getBaseContext(), "Usuario ya existente.", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
                    aviso.show();
                }
                else{
                    ContentValues insert = new ContentValues();
                    insert.put("nUsuario", usu.getText().toString());
                    insert.put("pass", pass.getText().toString());

                    bd.insert("Usuarios", null, insert);
                    Intent i = new Intent (getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}