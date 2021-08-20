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
                int cantidadP = Integer.parseInt(dtv.getProducto().getExistencia_p() )- dtv.getCantidad_ve();

                modificarProducto(dtv.getProducto().getId(),cantidadP );
                values2.put("idVenta_ve", idVenta);


                idDetalle = db2.insert(TABLE_DETALLE_VENTA, null, values2);

            }
        }catch (Exception e){
            e.toString();
        }
        return idVenta;
    }
    public int numeroVenta(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int numero = 0;
        Cursor cursorCotizacion = null;

        cursorCotizacion = db.rawQuery("SELECT * FROM " + TABLE_VENTAS, null);
        if (cursorCotizacion.moveToLast()){
            numero = cursorCotizacion.getInt(0);
        }
        cursorCotizacion.close();
        return  numero;

    }
    public Venta buscarVenta(String clave){
        DBVenta dbVenta= new DBVenta(context);
        SQLiteDatabase  db = dbVenta.getWritableDatabase();
        Venta venta = null;
        Cursor cursor = null;
        int idProducto=0;
        int idVenta=0;
        int idCliente = 0;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_VENTAS + " WHERE clave_ve = '"+ clave + "'", null);
        if (cursor.moveToFirst()){
            venta = new Venta();


            venta.setClave_ve(cursor.getString(1));
            venta.setFecha_ve(cursor.getString(2));
            venta.setCantidadT_ve(cursor.getInt(3));
            venta.setIVA(cursor.getFloat(4));
            venta.setSubtotal(cursor.getFloat(5));
            venta.setTotal(cursor.getFloat(6));
            venta.setIdProducto_v(cursor.getInt(7));
            venta.setIdCliente_v(cursor.getInt(8));
            idVenta=cursor.getInt(0);

            Cliente c =new Cliente();
            c.setId(cursor.getInt(8));
            idCliente = cursor.getInt(8);
            venta.setCliente(c);
        }

        DBCliente dbCiente= new DBCliente(context);
        SQLiteDatabase  db1 = dbVenta.getWritableDatabase();
        Cliente cliente = null;
        Cursor cursor1 = null;

        cursor1 = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE idCliente = '"+ idCliente + "'", null);
        if (cursor1.moveToFirst()){
            cliente = new Cliente();
            System.out.println("antes");
            cliente.setId(cursor1.getInt(0));
            cliente.setClave_c(cursor1.getString(1));
            cliente.setNombre_c(cursor1.getString(2));
            cliente.setCalle_c(cursor1.getString(3));
            cliente.setColonia_c(cursor1.getString(4));
            cliente.setCiudad_c(cursor1.getString(5));
            cliente.setRFC_c(cursor1.getString(6));
            cliente.setTelefono_c(cursor1.getString(7));
            cliente.setEmail_c(cursor1.getString(8));
            cliente.setSaldo_c(cursor1.getFloat(9));
            System.out.println("despues");

        }
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();

        venta.setCliente(cliente);

        ArrayList<DetalleVenta> listaDV= new ArrayList<>();

        DetalleVenta dVenta = null;
        Producto producto = null;
        Cursor cursor2 = null;
        cursor2 = db3.rawQuery("SELECT * FROM " + TABLE_DETALLE_VENTA + " WHERE idVenta_ve = '"+ idVenta + "'", null);
        if (cursor2.moveToFirst()){
            do {
                dVenta = new DetalleVenta();

                dVenta.setId(cursor.getInt(0));
                dVenta.setClave_c(cursor2.getString(1));
                dVenta.setUnidad_ve(cursor2.getString(2));
                dVenta.setCantidad_ve(cursor2.getInt(3));
                dVenta.setPrecio_ve(cursor2.getInt(4));
                dVenta.setImporte_ve(cursor2.getInt(5));
                dVenta.setIdProducto_ve(cursor2.getInt(6));

                producto = buscar(cursor2.getInt(6));
                dVenta.setProducto(producto);

                listaDV.add(dVenta);


            }while(cursor2.moveToNext());
        }
        venta.setDetalleVentaA(listaDV);
        cursor.close();


        return  venta;

    }

    public Producto buscar(int id){
        DBProducto dbProducto= new DBProducto(context);
        SQLiteDatabase  db = dbProducto.getWritableDatabase();
        Producto producto = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " WHERE idProducto = '"+ id + "'", null);
        if (cursor.moveToFirst()){
            producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setClave_p(cursor.getString(1));
            producto.setNombre_p(cursor.getString(2));
            producto.setLinea_p(cursor.getString(3));
            producto.setExistencia_p(cursor.getString(4));
            producto.setPrecioCosto_p(cursor.getFloat(5));
            producto.setPrecioVenta1_p(cursor.getFloat(6));
            producto.setPrecioventa2_p(cursor.getFloat(7));
            //producto.setPrecioPromedio_p(cursor.getFloat(8));

        }
        cursor.close();
        return  producto;
    }

    public boolean modificarProducto(int id, int cantidad){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET existencia_p = '"+cantidad+ "' where idProducto = '"+id+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }
    public boolean eliminarVenta(int clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_VENTAS +" where clave_ve = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }


}
