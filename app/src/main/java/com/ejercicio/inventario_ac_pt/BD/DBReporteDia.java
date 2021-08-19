package com.ejercicio.inventario_ac_pt.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Cliente;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;
import com.ejercicio.inventario_ac_pt.entidades.Venta;

import java.util.ArrayList;

public class DBReporteDia  extends DBHelper {
    Context context;

    public DBReporteDia(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Venta> listaVentas(String fecha){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Venta> listaVenta= new ArrayList<>();

        Venta venta = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_VENTAS + " WHERE fecha_ve = '"+ fecha + "'", null);
        if (cursor.moveToFirst()){
            do {
                venta = new Venta();
                venta.setId(cursor.getInt(0));
                venta.setClave_ve(cursor.getString(1));
                venta.setFecha_ve(cursor.getString(2));
                venta.setCantidadT_ve(cursor.getInt(3));
                venta.setIVA(cursor.getFloat(4));
                venta.setSubtotal(cursor.getFloat(5));
                venta.setTotal(cursor.getFloat(6));
                Cliente cl = new Cliente();
                cl = buscarCliente(cursor.getInt(8));
                venta.setCliente(cl);
                listaVenta.add(venta);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  listaVenta;

    }
    public Cliente buscarCliente(int id){
        DBCliente dbCliente= new DBCliente(context);
        SQLiteDatabase  db = dbCliente.getWritableDatabase();
        Cliente cliente = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE idCliente = '"+ id + "'", null);
        if (cursor.moveToFirst()){
            cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setClave_c(cursor.getString(1));
            cliente.setNombre_c(cursor.getString(2));
            cliente.setCalle_c(cursor.getString(3));
            cliente.setColonia_c(cursor.getString(4));
            cliente.setCiudad_c(cursor.getString(5));
            cliente.setRFC_c(cursor.getString(6));
            cliente.setTelefono_c(cursor.getString(7));
            cliente.setEmail_c(cursor.getString(8));
            cliente.setSaldo_c(cursor.getInt(9));

        }
        cursor.close();
        return  cliente;

    }
}
