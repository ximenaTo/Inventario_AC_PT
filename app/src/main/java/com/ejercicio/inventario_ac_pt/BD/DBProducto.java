package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;

import java.util.ArrayList;

public class DBProducto extends DBHelper {
    Context context;

    public DBProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarProducto(String clave_p, String nombre_p, String linea_p,
                                  String existencia_p, float precioCosto_p, /*float precioPromedio_p,*/
                                 float precioVenta1_p, float precioventa2_p){

        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_p", clave_p);
            values.put("nombre_p", nombre_p);
            values.put("linea_p", linea_p);
            //values.put("unidad_p", "par");
            values.put("existencia_p", existencia_p);
            values.put("precioCosto_p", precioCosto_p);
            values.put("precioPromedio_p", 0.0);
            values.put("precioVenta1_p", precioVenta1_p);
            values.put("precioventa2_p", precioventa2_p);
            id = db.insert(TABLE_PRODUCTO, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }

    public boolean modificarProducto(String clave_p,
                                     String nombre_p,
                                     String linea_p,
                                     String existencia_p,
                                     float precioCosto_p,
                                     //float precioPromedio_p,
                                     float precioVenta1_p,
                                     float precioventa2_p){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET clave_p = '"+clave_p+"', " + "nombre_p = '"+ nombre_p+"', " +
                    "linea_p = '"+ linea_p+"', " + "existencia_p = '"+ existencia_p+"', " + "precioCosto_p = '"+ precioCosto_p+"', " +
                    /*"precioPromedio_p = '"+precioPromedio_p+"',"  + */ "precioVenta1_p = '"+precioVenta1_p+"',"   +  "precioventa2_p = '"+precioventa2_p  + "' where clave_p = '"+clave_p+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }

    public boolean eliminarProducto(String clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_PRODUCTO +" where clave_p = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }

    public ArrayList<Producto> listaProductos(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Producto> listaProducto= new ArrayList<>();

        Producto producto = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO, null);
        if (cursor.moveToFirst()){
            do {
                producto = new Producto();
                producto.setId(cursor.getInt(0));
                producto.setClave_p(cursor.getString(1));
                producto.setNombre_p(cursor.getString(2));
                producto.setLinea_p(cursor.getString(3));
                producto.setExistencia_p(cursor.getString(4));
                producto.setPrecioCosto_p(cursor.getFloat(5));
                producto.setPrecioPromedio_p(cursor.getFloat(6));
                producto.setPrecioVenta1_p(cursor.getFloat(7));
                producto.setPrecioventa2_p(cursor.getFloat(8));
                listaProducto.add(producto);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  listaProducto;

    }

    public Producto buscarProducto(String clave){
        DBProducto dbProducto= new DBProducto(context);
        SQLiteDatabase  db = dbProducto.getWritableDatabase();
        Producto producto = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " WHERE clave_p = '"+ clave + "'", null);
        if (cursor.moveToFirst()){
            producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setClave_p(cursor.getString(1));
            producto.setNombre_p(cursor.getString(2));
            producto.setLinea_p(cursor.getString(3));
            producto.setExistencia_p(cursor.getString(4));
            producto.setPrecioCosto_p(cursor.getFloat(5));
            producto.setPrecioVenta1_p(cursor.getFloat(7));
            producto.setPrecioventa2_p(cursor.getFloat(8));
            //producto.setPrecioPromedio_p(cursor.getFloat(8));

        }
        cursor.close();
        return  producto;

    }

}
