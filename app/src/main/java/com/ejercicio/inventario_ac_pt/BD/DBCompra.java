package com.ejercicio.inventario_ac_pt.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Proveedor;

import java.util.ArrayList;

public class DBCompra extends DBHelper {
    Context context;

    public DBCompra(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public int numeroCompra(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int numero = 0;
        Cursor cursorCotizacion = null;

        cursorCotizacion = db.rawQuery("SELECT * FROM " + TABLE_COMPRAS, null);
        if (cursorCotizacion.moveToLast()){
            numero = cursorCotizacion.getInt(0);
        }
        cursorCotizacion.close();
        return  numero;

    }


}
