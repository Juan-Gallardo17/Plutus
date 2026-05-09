package com.maxcode.plutus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxcode.plutus.database.DatabaseHelper;
import com.maxcode.plutus.model.Gasto;

import java.util.ArrayList;
import java.util.List;

public class GastoDAO {

    private DatabaseHelper dbHelper;

    public GastoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Gasto gasto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_GASTO_NOMBRE, gasto.getNombre());
        values.put(DatabaseHelper.COL_GASTO_MONTO, gasto.getMonto());
        values.put(DatabaseHelper.COL_GASTO_CATEGORIA, gasto.getCategoria());
        values.put(DatabaseHelper.COL_GASTO_FECHA, gasto.getFecha());
        long id = db.insert(DatabaseHelper.TABLE_GASTOS, null, values);
        db.close();
        return id;
    }

    public List<Gasto> obtenerTodos() {
        List<Gasto> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_GASTOS,
                null, null, null, null, null,
                DatabaseHelper.COL_GASTO_FECHA + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Gasto g = new Gasto();
                g.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GASTO_ID)));
                g.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GASTO_NOMBRE)));
                g.setMonto(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GASTO_MONTO)));
                g.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GASTO_CATEGORIA)));
                g.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GASTO_FECHA)));
                lista.add(g);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_GASTOS,
                DatabaseHelper.COL_GASTO_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public double obtenerTotalGastos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double total = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.COL_GASTO_MONTO + ") FROM " + DatabaseHelper.TABLE_GASTOS, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return total;
    }
}