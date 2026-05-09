package com.maxcode.plutus.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "plutus.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Gastos
    public static final String TABLE_GASTOS = "gastos";
    public static final String COL_GASTO_ID = "id";
    public static final String COL_GASTO_NOMBRE = "nombre";
    public static final String COL_GASTO_MONTO = "monto";
    public static final String COL_GASTO_CATEGORIA = "categoria";
    public static final String COL_GASTO_FECHA = "fecha";

    // Tabla Metas
    public static final String TABLE_METAS = "metas";
    public static final String COL_META_ID = "id";
    public static final String COL_META_NOMBRE = "nombre";
    public static final String COL_META_MONTO_OBJETIVO = "monto_objetivo";
    public static final String COL_META_MONTO_ACTUAL = "monto_actual";
    public static final String COL_META_FECHA_LIMITE = "fecha_limite";

    // Tabla Deudas
    public static final String TABLE_DEUDAS = "deudas";
    public static final String COL_DEUDA_ID = "id";
    public static final String COL_DEUDA_NOMBRE = "nombre";
    public static final String COL_DEUDA_MONTO = "monto";
    public static final String COL_DEUDA_FECHA_LIMITE = "fecha_limite";
    public static final String COL_DEUDA_ESTADO = "estado";

    private static final String CREATE_TABLE_GASTOS =
            "CREATE TABLE " + TABLE_GASTOS + " (" +
                    COL_GASTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_GASTO_NOMBRE + " TEXT NOT NULL, " +
                    COL_GASTO_MONTO + " REAL NOT NULL, " +
                    COL_GASTO_CATEGORIA + " TEXT NOT NULL, " +
                    COL_GASTO_FECHA + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_METAS =
            "CREATE TABLE " + TABLE_METAS + " (" +
                    COL_META_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_META_NOMBRE + " TEXT NOT NULL, " +
                    COL_META_MONTO_OBJETIVO + " REAL NOT NULL, " +
                    COL_META_MONTO_ACTUAL + " REAL DEFAULT 0, " +
                    COL_META_FECHA_LIMITE + " TEXT);";

    private static final String CREATE_TABLE_DEUDAS =
            "CREATE TABLE " + TABLE_DEUDAS + " (" +
                    COL_DEUDA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_DEUDA_NOMBRE + " TEXT NOT NULL, " +
                    COL_DEUDA_MONTO + " REAL NOT NULL, " +
                    COL_DEUDA_FECHA_LIMITE + " TEXT, " +
                    COL_DEUDA_ESTADO + " TEXT DEFAULT 'pendiente');";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GASTOS);
        db.execSQL(CREATE_TABLE_METAS);
        db.execSQL(CREATE_TABLE_DEUDAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_METAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEUDAS);
        onCreate(db);
    }
}