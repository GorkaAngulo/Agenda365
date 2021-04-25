package com.example.entrega1;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ConexionBDAWS_registro2 extends Worker {
    public ConexionBDAWS_registro2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        // abrir conexion
        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/gangulo010/WEB/registro2.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // params
        String txtUsuario = getInputData().getString("usuario");
        String txtContraseña = getInputData().getString("contraseña");
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("nUsuario", txtUsuario).appendQueryParameter("pass", txtContraseña);
        String parametros = builder.build().getEncodedQuery();

        // configurar urlconnection
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        // escribir parametros en la llamada
        PrintWriter out = null;
        try {
            out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();

            // ejecutar llamada
            int statusCode = urlConnection.getResponseCode();

            // coger resultado
            String result = null;
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = "";
                result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Data resultados = new Data.Builder()
                .putInt("resultado", 42)
                .build();
        return ListenableWorker.Result.success(resultados);
    }
}
