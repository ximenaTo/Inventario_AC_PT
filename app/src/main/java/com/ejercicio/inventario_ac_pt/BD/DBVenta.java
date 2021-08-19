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

public class DBVenta extends DBHelper {

    Context context;

    public DBVenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarVenta(String clave_v,String fecha,int cantidadTotalV,float IVA,
                                float subtotal, float total, int idProducto_v,int idCliente_v){
        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_ve", clave_v);
            values.put("fecha_ve", fecha);
            values.put("cantidadT_ve", cantidadTotalV);
            values.put("IVA", IVA);
            values.put("subtotal", subtotal);
            values.put("total", total);
            values.put("idProducto_v", idProducto_v);
            values.put("idCliente_v", idCliente_v);
            id = db.insert(TABLE_VENTAS, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }


}
