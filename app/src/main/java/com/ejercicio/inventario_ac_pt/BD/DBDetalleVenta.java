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

    public DetalleVenta buscarVenta(String clave){
        DBDetalleVenta dbDVenta= new DBDetalleVenta(context);
        SQLiteDatabase  db = dbDVenta.getWritableDatabase();

        Producto producto = null;
        DetalleVenta dVenta = null;
        Venta venta = null;
        Cliente cliente = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_DETALLE_VENTA + " WHERE clave_c = '"+ clave + "'", null);
        if (cursor.moveToFirst()){
            producto = new Producto();
            dVenta = new DetalleVenta();
            venta = new Venta();
            cliente = new Cliente();

            dVenta.setId(cursor.getInt(0));
            dVenta.setUnidad_ve(cursor.getString(2));
            dVenta.setCantidad_ve(cursor.getInt(3));
            dVenta.setPrecio_ve(cursor.getInt(4));
            dVenta.setImporte_ve(cursor.getInt(5));
            dVenta.setIdProducto_ve(cursor.getInt(6));

            venta.setClave_ve(cursor.getString(1));
            venta.setCantidadT_ve(cursor.getInt(3));
            venta.setSubtotal(cursor.getFloat(6));
            venta.setIVA(cursor.getFloat(5));
            venta.setTotal(cursor.getFloat(7));

            producto.setId(cursor.getInt(0));
            producto.setClave_p(cursor.getString(1));
            producto.setNombre_p(cursor.getString(2));
            producto.setLinea_p(cursor.getString(3));
            producto.setExistencia_p(cursor.getString(4));
            producto.setPrecioCosto_p(cursor.getFloat(5));
            producto.setPrecioVenta1_p(cursor.getFloat(6));
            producto.setPrecioventa2_p(cursor.getFloat(7));

            cliente.setClave_c(cursor.getString(1));
            cliente.setNombre_c(cursor.getString(2));

            venta.setProducto(producto);
            venta.setCliente(cliente);

            dVenta.setVenta(venta);
            dVenta.setProducto(producto);

        }
        cursor.close();
        return  dVenta;

    }
}
