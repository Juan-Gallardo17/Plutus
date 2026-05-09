package com.maxcode.plutus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxcode.plutus.database.DatabaseHelper;
import com.maxcode.plutus.model.Deuda;

import java.util.ArrayList;
import java.util.List;

public class DeudaDAO {

    private DatabaseHelper dbHelper;

    public DeudaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Deuda deuda) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DEUDA_NOMBRE, deuda.getNombre());
        values.put(DatabaseHelper.COL_DEUDA_MONTO, deuda.getMonto());
        values.put(DatabaseHelper.COL_DEUDA_FECHA_LIMITE, deuda.getFechaLimite());
        values.put(DatabaseHelper.COL_DEUDA_ESTADO, deuda.getEstado());
        long id = db.insert(DatabaseHelper.TABLE_DEUDAS, null, values);
        db.close();
        return id;
    }

    public List<Deuda> obtenerTodos() {
        List<Deuda> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_DEUDAS,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Deuda d = new Deuda();
                d.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DEUDA_ID)));
                d.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DEUDA_NOMBRE)));
                d.setMonto(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DEUDA_MONTO)));
                d.setFechaLimite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DEUDA_FECHA_LIMITE)));
                d.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DEUDA_ESTADO)));
                lista.add(d);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public int actualizarEstado(int id, String nuevoEstado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DEUDA_ESTADO, nuevoEstado);
        int rows = db.update(DatabaseHelper.TABLE_DEUDAS, values,
                DatabaseHelper.COL_DEUDA_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_DEUDAS,
                DatabaseHelper.COL_DEUDA_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public double obtenerTotalPendiente() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double total = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.COL_DEUDA_MONTO + ") FROM " +
                DatabaseHelper.TABLE_DEUDAS + " WHERE " +
                DatabaseHelper.COL_DEUDA_ESTADO + " != 'pagado'", null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return total;
    }
}