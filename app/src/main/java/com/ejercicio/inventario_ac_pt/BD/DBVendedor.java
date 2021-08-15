package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Vendedor;

import java.util.ArrayList;

public class DBVendedor extends DBHelper {

    Context context;

    public DBVendedor(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarVendedor(String clave, String nombre, String calle,
                                 String colonio, String telefono, String email, float comision){
        long id =0;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("clave_v", clave);
            values.put("nombre_v", nombre);
            values.put("calle_v", calle);
            values.put("colonia_v", colonio);
            values.put("telefono_v", telefono);
            values.put("email_v", email);
            values.put("comisiones_v", comision);


            id = db.insert(TABLE_VENDEDOR, null, values);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public boolean modificarVendedor(String clave, String nombre, String calle,
                                     String colonio, String telefono, String email, float comision){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_VENDEDOR + " SET nombre_v = '"+nombre+"', " + "calle_v = '"+ calle+"', " + "colonia_v = '"+ colonio+"', " + "telefono_v = '"+ telefono+"', " + "email_v = '"+ email+"', " + "comisiones_v = '"+comision  + "' where clave_v = '"+clave+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }

    public boolean eliminarVendedor(String clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_VENDEDOR +" where clave_v = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }

    public Vendedor buscarVendedor(String clave){
        DBVendedor dbVendedor= new DBVendedor(context);
        SQLiteDatabase  db = dbVendedor.getWritableDatabase();
        Vendedor vendedor = null;
        Cursor cursorVendedor = null;

        cursorVendedor = db.rawQuery("SELECT * FROM " + TABLE_VENDEDOR + " WHERE clave_v = '"+ clave + "'", null);
        if (cursorVendedor.moveToFirst()){
            vendedor = new Vendedor();
            vendedor.setId(cursorVendedor.getInt(0));
            vendedor.setClave(cursorVendedor.getString(1));
            vendedor.setNombre(cursorVendedor.getString(2));
            vendedor.setCalle(cursorVendedor.getString(3));
            vendedor.setColonio(cursorVendedor.getString(4));
            vendedor.setTelefono(cursorVendedor.getString(5));
            vendedor.setEmail(cursorVendedor.getString(6));
            vendedor.setComision(cursorVendedor.getInt(7));

        }
        cursorVendedor.close();
        return  vendedor;

    }
    public ArrayList<Vendedor> listaVendedores(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Vendedor> listaVendedor= new ArrayList<>();

        Vendedor vendedor = null;
        Cursor cursorVendedor = null;

        cursorVendedor = db.rawQuery("SELECT * FROM " + TABLE_VENDEDOR, null);
        if (cursorVendedor.moveToFirst()){
            do {
                vendedor = new Vendedor();
                vendedor.setId(cursorVendedor.getInt(0));
                vendedor.setClave(cursorVendedor.getString(1));
                vendedor.setNombre(cursorVendedor.getString(2));
                vendedor.setCalle(cursorVendedor.getString(3));
                vendedor.setColonio(cursorVendedor.getString(4));
                vendedor.setTelefono(cursorVendedor.getString(5));
                vendedor.setEmail(cursorVendedor.getString(6));
                vendedor.setComision(cursorVendedor.getInt(7));
                listaVendedor.add(vendedor);
            }while(cursorVendedor.moveToNext());
        }
        cursorVendedor.close();
        return  listaVendedor;

    }


}
