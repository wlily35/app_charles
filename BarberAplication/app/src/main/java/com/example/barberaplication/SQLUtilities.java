package com.example.barberaplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLUtilities extends SQLiteOpenHelper {

    private static final String DB_NAME = "barberia.db";
    private static final int DB_VERSION = 1;

    String sqlCreate = "CREATE TABLE Material (id TEXT, nombre TEXT, cantidad TEXT, tipo TEXT)";

    public SQLUtilities(Context context) {super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Material");
        onCreate(sqLiteDatabase);

    }
}
