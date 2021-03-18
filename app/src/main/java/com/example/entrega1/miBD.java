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

        // ARTISTAS
        db.execSQL("CREATE TABLE Artistas ('ID' PRIMARY KEY " +
                "NOT NULL, 'NombreCompleto' VARCHAR(255), 'Nacimiento' VARCHAR(255), " +
                "'Muerte' VARCHAR(255))");


        // ÁLBUMES
        db.execSQL("CREATE TABLE Álbumes ('ID' PRIMARY KEY " +
                "NOT NULL, 'Titulo' VARCHAR(255), 'Autor' VARCHAR(255), " +
                "'Género' VARCHAR(255), 'Nº Canciones' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}


