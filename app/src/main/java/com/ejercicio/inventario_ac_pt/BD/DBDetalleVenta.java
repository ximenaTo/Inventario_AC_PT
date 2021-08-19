package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Cliente;
import com.ejercicio.inventario_ac_pt.entidades.DetalleVenta;
import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Venta;

import java.util.ArrayList;

public class DBDetalleVenta extends DBHelper {

    Context context;

    public DBDetalleVenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarDetalle(String clave_c,String unidad_ve,int cantidad_ve,float precio_ve,
                              float importe_ve, int idProducto_ve, int idVenta_ve){
        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_c", clave_c);
            values.put("unidad_ve", unidad_ve);
            values.put("cantidad_ve", cantidad_ve);
            values.put("precio_ve", precio_ve);
            values.put("importe_ve", importe_ve);
            values.put("idProducto_ve", idProducto_ve);
            values.put("idVenta_ve", idVenta_ve);
            id = db.insert(TABLE_DETALLE_VENTA, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
}
