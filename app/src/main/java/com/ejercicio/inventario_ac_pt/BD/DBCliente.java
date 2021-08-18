package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Cliente;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;

import java.util.ArrayList;

public class DBCliente extends DBHelper{
    Context context;

    public DBCliente(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarCliente(String clave_c, String nombre_c, String calle_c,
                                  String colonia_c, String ciudad_c, String RFC_c, String telefono_c,
                                  String email_c, float saldo ){

        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_c", clave_c);
            values.put("nombre_c", nombre_c);
            values.put("calle_c", calle_c);
            values.put("colonia_c", colonia_c);
            values.put("ciudad_c", ciudad_c);
            values.put("RFC_c", RFC_c);
            values.put("telefono_c", telefono_c);
            values.put("email_c", email_c);
            values.put("saldo_c", saldo);
            id = db.insert(TABLE_CLIENTE, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public boolean modificarCliente(String clave_c, String nombre_c, String calle_c,
                                      String colonia_c, String ciudad_c, String RFC_c, String telefono_c,
                                      String email_c, float saldo){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_CLIENTE + " SET nombre_c = '"+nombre_c+"', " + "calle_c = '"+ calle_c+"', " +
                    "colonia_c = '"+ colonia_c+"', " + "ciudad_c = '"+ ciudad_c+"', " + "RFC_c = '"+ RFC_c+"', " +
                    "telefono_c = '"+telefono_c+"',"  +  "email_c = '"+email_c+"',"   +  "saldo_c = '"+saldo  + "' where clave_c = '"+clave_c+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }

    public boolean eliminarCliente(String clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_CLIENTE +" where clave_c = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }

    public Cliente buscarCliente(String clave){
        DBCliente dbCliente= new DBCliente(context);
        SQLiteDatabase  db = dbCliente.getWritableDatabase();
        Cliente cliente = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE clave_c = '"+ clave + "'", null);
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
    public ArrayList<Cliente> listaClientes(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Cliente> listaCliente= new ArrayList<>();

        Cliente cliente = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE, null);
        if (cursor.moveToFirst()){
            do {
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
                listaCliente.add(cliente);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  listaCliente;

    }

}
