package com.ejercicio.inventario_ac_pt.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "inventario.db";

    public static final String TABLE_VENDEDOR = "vendedor";
    public static final String TABLE_CLIENTE = "cliente";
    public static final String TABLE_PROVEEDOR = "proveedor";
    public static final String TABLE_PRODUCTO = "producto";
    public static final String TABLE_COMPRAS = "compras";
    public static final String TABLE_VENTAS = "ventas";
    public static final String TABLE_REPORTES = "reportes";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_VENDEDOR + "(" +
                "idVendedor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_v TEXT NOT NULL, "+
                "nombre_v TEXT NOT NULL,"+
                "calle_v TEXT NOT NULL,"+
                "colonia_v TEXT NOT NULL,"+
                "telefono_v TEXT NOT NULL,"+
                "email_v TEXT NOT NULL,"+
                "comisiones_v INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CLIENTE + "(" +
                "idCliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_c TEXT NOT NULL, "+
                "nombre_c TEXT NOT NULL,"+
                "calle_c TEXT NOT NULL,"+
                "colonia_c TEXT NOT NULL,"+
                "ciudad_c TEXT NOT NULL,"+
                "RFC_c TEXT NOT NULL,"+
                "telefono_c TEXT NOT NULL,"+
                "email_c TEXT NOT NULL,"+
                "saldo_c INTEGER  NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PROVEEDOR + "(" +
                "idProveedor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_pr TEXT NOT NULL, "+
                "nombre_pr TEXT NOT NULL,"+
                "calle_pr TEXT NOT NULL,"+
                "colonia_pr TEXT NOT NULL,"+
                "ciudad_pr TEXT NOT NULL,"+
                "RFC_pr TEXT NOT NULL,"+
                "telefono_pr TEXT NOT NULL,"+
                "email_pr TEXT NOT NULL,"+
                "saldo_pr INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTO + "(" +
                "idProducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_p TEXT NOT NULL, "+
                "nombre_p TEXT NOT NULL,"+
                "linea_p TEXT NOT NULL,"+
                "existencia_p TEXT NOT NULL,"+
                "precioCosto_p INTEGER  NOT NULL,"+
                "precioPromedio_p INTEGER  NOT NULL,"+
                "precioVenta1_p INTEGER  NOT NULL,"+
                "precioventa2_p INTEGER  NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_COMPRAS + "(" +
                "idCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_c TEXT NOT NULL, "+
                "fecha_c TEXT NOT NULL,"+
                "unidad_c TEXT NOT NULL,"+
                "cantidad_c INTEGER NOT NULL,"+
                "importe_c INTEGER NOT NULL,"+
                "FOREIGN KEY(idProveedor_c) REFERENCES "+ TABLE_PROVEEDOR +"(idProveedor)," +
                "FOREIGN KEY(idProducto_c) REFERENCES "+ TABLE_PRODUCTO +"(idProducto))");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_VENTAS + "(" +
                "idCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_ve TEXT NOT NULL, "+
                "fecha_ve TEXT NOT NULL,"+
                "unidad_ve TEXT NOT NULL,"+
                "cantidad_ve INTEGER NOT NULL,"+
                "importe_ve INTEGER NOT NULL,"+
                "FOREIGN KEY(idCliente_v) REFERENCES "+ TABLE_CLIENTE +"(idCliente)," +
                "FOREIGN KEY(idProducto_v) REFERENCES "+ TABLE_PRODUCTO +"(idProducto)," +
                "FOREIGN KEY(idProveedor_v) REFERENCES "+ TABLE_VENDEDOR +"(idProveedor))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
