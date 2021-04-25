package com.example.entrega1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

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
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usu = findViewById(R.id.usuNuevo);
                TextView pass = findViewById(R.id.passNueva);

                gestionarConexion2(usu, pass);
            }
        });
    }

    private void gestionarConexion(TextView usu, TextView pass) {
        Data datos = new Data.Builder().putString("usuario", usu.getText().toString()).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_registro1.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
//                            if (workInfo.getOutputData().getString("resultado").equals("true")) {
//                                int tiempo = Toast.LENGTH_SHORT;
//                                Toast aviso = Toast.makeText(getBaseContext(), "Usuario ya existente.", tiempo);
//                                aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 23, 17);
//                                aviso.show();
//                            } else {
                            gestionarConexion2(usu, pass);
//                            }
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }

    private void gestionarConexion2(TextView usu, TextView pass) {
        Data datos = new Data.Builder().putString("usuario", usu.getText().toString()).
                putString("contraseña", pass.getText().toString()).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDAWS_registro2.class).setInputData(datos).build();
        WorkManager.getInstance(getBaseContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Intent i = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
        WorkManager.getInstance(getBaseContext()).enqueue(otwr);
    }
}