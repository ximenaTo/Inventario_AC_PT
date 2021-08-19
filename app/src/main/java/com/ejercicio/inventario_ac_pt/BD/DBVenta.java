package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.DetalleVenta;
import com.ejercicio.inventario_ac_pt.entidades.Venta;

import java.util.ArrayList;

public class DBVenta extends DBHelper {

    Context context;

    public DBVenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarVenta(ArrayList<DetalleVenta> listaDetalle, Venta v){
        long idVenta =0;
        long idDetalle;
        System.out.println("Tamanio"+listaDetalle.size());
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_ve", v.getClave_ve());
            values.put("fecha_ve", v.getFecha_ve());
            values.put("cantidadT_ve", v.getFecha_ve());
            values.put("IVA", v.getIVA());
            values.put("subtotal", v.getSubtotal());
            values.put("total", v.getTotal());
            values.put("idProducto_v", 0);
            values.put("idCliente_v", v.getCliente().getId());
            idVenta = db.insert(TABLE_VENTAS, null, values);

            for (DetalleVenta dtv: listaDetalle) {
                DBHelper dbHelper2 = new DBHelper(context);
                SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("clave_c", "dc"+idVenta);
                values2.put("unidad_ve","Par");
                values2.put("cantidad_ve", dtv.getCantidad_ve());
                values2.put("precio_ve", dtv.getPrecio_ve());
                values2.put("importe_ve", dtv.getImporte_ve());
                values2.put("idProducto_ve", dtv.getProducto().getId());
                System.out.println(dtv.getProducto().getId());
                System.out.println(dtv.getProducto().getNombre_p());

                values2.put("idVenta_ve", idVenta);


                idDetalle = db2.insert(TABLE_DETALLE_VENTA, null, values2);

            }
        }catch (Exception e){
            e.toString();
        }
        return idVenta;
    }


}
