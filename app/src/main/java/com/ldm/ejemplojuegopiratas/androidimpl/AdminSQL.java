package com.ldm.ejemplojuegopiratas.androidimpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQL extends SQLiteOpenHelper {
    public AdminSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql){
        sql.execSQL("create table  puntuaciones(codigo Integer PRIMARY KEY, puntos INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        sql.execSQL("DROP TABLE IF EXISTS puntuaciones");
    }
}
