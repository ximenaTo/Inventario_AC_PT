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
    public static final String TABLE_DETALLE_COMPRA = "detalle_compra";
    public static final String TABLE_VENTAS = "ventas";
    public static final String TABLE_DETALLE_VENTA = "detalle_venta";
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
                "comisiones_v FLOAT NOT NULL)");

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
                "saldo_c FLOAT DEFAULT 0.0)");

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
                "saldo_pr FLOAT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTO + "(" +
                "idProducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_p TEXT NOT NULL, "+
                "nombre_p TEXT NOT NULL,"+
                "linea_p TEXT NOT NULL,"+
                //"unidad_p TEXT NOT NULL,"+
                "existencia_p TEXT NOT NULL,"+
                "precioCosto_p FLOAT  NOT NULL,"+
                "precioPromedio_p FLOAT  DEFAULT 0.0,"+
                "precioVenta1_p FLOAT  NOT NULL,"+
                "precioventa2_p FLOAT  NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_COMPRAS + "(" +
                "idCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_c TEXT NOT NULL, "+
                "fecha_c TEXT NOT NULL,"+
                "IVA FLOAT NOT NULL,"+
                "subtotal FLOAT NOT NULL,"+
                "total FLOAT NOT NULL,"+
                "idProveedor_c INTEGER NOT NULL," +
                "idVendedor_c INTEGER NOT NULL," +
                "FOREIGN KEY(idProveedor_c) REFERENCES "+ TABLE_PROVEEDOR +"(idProveedor),"+
                "FOREIGN KEY(idVendedor_c) REFERENCES "+ TABLE_VENDEDOR +"(idVendedor))");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DETALLE_COMPRA + "(" +
                "idDetalleCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_c TEXT NOT NULL, "+
                "unidad_c TEXT NOT NULL,"+
                "cantidad_c INTEGER NOT NULL,"+
                "precio_ve FLOAT NOT NULL,"+
                "importe_c FLOAT NOT NULL,"+
                "idCompra INTEGER NOT NULL,"+
                "idProducto_c INTEGER NOT NULL,"+
                "FOREIGN KEY(idCompra) REFERENCES "+ TABLE_COMPRAS +"(idCompra)," +
                "FOREIGN KEY(idProducto_c) REFERENCES "+ TABLE_PRODUCTO +"(idProducto))");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_VENTAS + "(" +
                "idVenta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_ve TEXT NOT NULL, "+
                "fecha_ve TEXT NOT NULL,"+
                //"comision_ve FLOAT NOT NULL ,"+
                "cantidadT_ve INTEGER NOT NULL,"+
                "IVA FLOAT NOT NULL,"+
                "subtotal FLOAT NOT NULL,"+
                "total FLOAT NOT NULL,"+
                "idProducto_v INTEGER NOT NULL,"+
                "idCliente_v INTEGER NOT NULL,"+
                "FOREIGN KEY(idCliente_v) REFERENCES "+ TABLE_CLIENTE +"(idCliente)," +
                "FOREIGN KEY(idProducto_v) REFERENCES "+ TABLE_PRODUCTO +"(idProducto))");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DETALLE_VENTA + "(" +
                "idDetalleCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "clave_c TEXT NOT NULL, "+
                "unidad_ve TEXT NOT NULL,"+
                "cantidad_ve INTEGER NOT NULL,"+
                "precio_ve FLOAT NOT NULL,"+
                "importe_ve FLOAT NOT NULL,"+
                "idProducto_ve INTEGER NOT NULL,"+
                "idVenta_ve INTEGER NOT NULL,"+
                "FOREIGN KEY(idVenta_ve) REFERENCES "+ TABLE_VENTAS +"(idVenta)," +
                "FOREIGN KEY(idProducto_ve) REFERENCES "+ TABLE_PRODUCTO +"(idProducto))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_VENDEDOR);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CLIENTE);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PROVEEDOR);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PRODUCTO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_COMPRAS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_VENTAS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_REPORTES);
        onCreate(sqLiteDatabase);
    }
}
