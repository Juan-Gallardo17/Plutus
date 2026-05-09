package com.maxcode.plutus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxcode.plutus.database.DatabaseHelper;
import com.maxcode.plutus.model.Meta;

import java.util.ArrayList;
import java.util.List;

public class MetaDAO {

    private DatabaseHelper dbHelper;

    public MetaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Meta meta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_META_NOMBRE, meta.getNombre());
        values.put(DatabaseHelper.COL_META_MONTO_OBJETIVO, meta.getMontoObjetivo());
        values.put(DatabaseHelper.COL_META_MONTO_ACTUAL, meta.getMontoActual());
        values.put(DatabaseHelper.COL_META_FECHA_LIMITE, meta.getFechaLimite());
        long id = db.insert(DatabaseHelper.TABLE_METAS, null, values);
        db.close();
        return id;
    }

    public List<Meta> obtenerTodos() {
        List<Meta> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_METAS,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Meta m = new Meta();
                m.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_META_ID)));
                m.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_META_NOMBRE)));
                m.setMontoObjetivo(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_META_MONTO_OBJETIVO)));
                m.setMontoActual(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_META_MONTO_ACTUAL)));
                m.setFechaLimite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_META_FECHA_LIMITE)));
                lista.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public int actualizar(Meta meta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_META_MONTO_ACTUAL, meta.getMontoActual());
        int rows = db.update(DatabaseHelper.TABLE_METAS, values,
                DatabaseHelper.COL_META_ID + "=?",
                new String[]{String.valueOf(meta.getId())});
        db.close();
        return rows;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_METAS,
                DatabaseHelper.COL_META_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}