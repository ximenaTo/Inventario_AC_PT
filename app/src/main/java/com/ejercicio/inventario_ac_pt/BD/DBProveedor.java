package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Proveedor;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;

import java.util.ArrayList;

public class DBProveedor extends DBHelper {

    Context context;

    public DBProveedor(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public long insertarProveedor(String clave_pr, String nombre_pr, String calle_pr,
                                 String colonia_pr, String ciudad_pr, String RFC_pr, String telefono_pr,
                                  String email_pr, float saldo ){

        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_pr", clave_pr);
            values.put("nombre_pr", nombre_pr);
            values.put("calle_pr", calle_pr);
            values.put("colonia_pr", colonia_pr);
            values.put("ciudad_pr", ciudad_pr);
            values.put("RFC_pr", RFC_pr);
            values.put("telefono_pr", telefono_pr);
            values.put("email_pr", email_pr);
            values.put("saldo_pr", saldo);
            id = db.insert(TABLE_PROVEEDOR, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public boolean modificarProveedor(String clave_pr, String nombre_pr, String calle_pr,
                                     String colonia_pr, String ciudad_pr, String RFC_pr, String telefono_pr,
                                     String email_pr, float saldo ){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_PROVEEDOR + " SET nombre_pr = '"+nombre_pr+"', " + "calle_pr = '"+ calle_pr+"', " +
                        "colonia_pr = '"+ colonia_pr+"', " + "ciudad_pr = '"+ ciudad_pr+"', " + "RFC_pr = '"+ RFC_pr+"', " +
                        "telefono_pr = '"+telefono_pr+"',"  +  "email_pr = '"+email_pr+"',"   +  "saldo_pr = '"+saldo  + "' where clave_pr = '"+clave_pr+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }

    public boolean eliminarProveedor(String clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_PROVEEDOR +" where clave_pr = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }

    public Proveedor buscarProveedor(String clave){
        DBProveedor dbProveedor= new DBProveedor(context);
        SQLiteDatabase  db = dbProveedor.getWritableDatabase();
        Proveedor proveedor = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR + " WHERE clave_pr = '"+ clave + "'", null);
        if (cursor.moveToFirst()){
            proveedor = new Proveedor();
            proveedor.setId(cursor.getInt(0));
            proveedor.setClave_pr(cursor.getString(1));
            proveedor.setNombre_pr(cursor.getString(2));
            proveedor.setCalle_pr(cursor.getString(3));
            proveedor.setColonia_pr(cursor.getString(4));
            proveedor.setCiudad_pr(cursor.getString(5));
            proveedor.setRFC_pr(cursor.getString(6));
            proveedor.setTelefono_pr(cursor.getString(7));
            proveedor.setEmail_pr(cursor.getString(8));
            proveedor.setSaldo_pr(cursor.getInt(9));

        }
        cursor.close();
        return  proveedor;

    }
    public ArrayList<Proveedor> listaProveedores(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Proveedor> listaProveedor= new ArrayList<>();

        Proveedor proveedor = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR, null);
        if (cursor.moveToFirst()){
            do {
                proveedor = new Proveedor();
                proveedor.setId(cursor.getInt(0));
                proveedor.setClave_pr(cursor.getString(1));
                proveedor.setNombre_pr(cursor.getString(2));
                proveedor.setCalle_pr(cursor.getString(3));
                proveedor.setColonia_pr(cursor.getString(4));
                proveedor.setCiudad_pr(cursor.getString(5));
                proveedor.setRFC_pr(cursor.getString(6));
                proveedor.setTelefono_pr(cursor.getString(7));
                proveedor.setEmail_pr(cursor.getString(8));
                proveedor.setSaldo_pr(cursor.getInt(9));
                listaProveedor.add(proveedor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  listaProveedor;

    }

}
