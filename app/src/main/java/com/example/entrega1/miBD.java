package com.example.entrega1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class miBD extends SQLiteOpenHelper {

    private static miBD mInstance = null;

    private static final String DATABASE_NAME = "AgendaDB";
    private static final int DATABASE_VERSION = 1;

    private Context mCtx;

    public static miBD getInstance(@Nullable Context ctx) {
        if (mInstance == null) {
            mInstance = new miBD(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private miBD(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        mCtx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USUARIOS
        db.execSQL("CREATE TABLE Usuarios ('nUsuario' PRIMARY KEY " +
                "NOT NULL, 'pass' PASSWORD(255))");

        // TAREAS
        db.execSQL("CREATE TABLE Tareas ('Titulo' VARCHAR(50) PRIMARY KEY NOT NULL, 'Fecha' VARCHAR(255), " +
                "'Cuerpo' VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}


