package com.ejercicio.inventario_ac_pt.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ejercicio.inventario_ac_pt.entidades.Compra;
import com.ejercicio.inventario_ac_pt.entidades.DetalleCompra;
import com.ejercicio.inventario_ac_pt.entidades.PrecioProm;
import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;

import java.util.ArrayList;

public class DBCompra extends DBHelper {
    Context context;

    public DBCompra(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public int numeroCompra(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int numero = 0;
        Cursor cursorCotizacion = null;

        cursorCotizacion = db.rawQuery("SELECT * FROM " + TABLE_COMPRAS, null);
        if (cursorCotizacion.moveToLast()){
            numero = cursorCotizacion.getInt(0);
        }
        cursorCotizacion.close();
        return  numero;

    }

    public long insertarCompra(Compra c, ArrayList<DetalleCompra> dc, ArrayList<PrecioProm> pp){

        long idCompra =0;
        long idDetalle;
        try{

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("clave_c", c.getClave_c());
            values.put("fecha_c", c.getFecha_c());
            values.put("IVA", c.getIVA());
            values.put("subtotal", c.getSubtotal());
            values.put("total", c.getTotal());
            values.put("idProveedor_c", c.getProveedor().getId());
            values.put("idVendedor_c", 0);
            idCompra = db.insert(TABLE_COMPRAS, null, values);

            float arre[] = new float[pp.size()];
            int j=0;
            for(PrecioProm prs: pp){
                arre[j]=prs.getPrecio_p();
                j++;

            }
            int k=0;
            for(DetalleCompra dt: dc){
                DBHelper dbHelper2 = new DBHelper(context);
                SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("clave_c", "dc"+dt.getClave_c());
                values2.put("unidad_c","Par");
                values2.put("cantidad_c", dt.getCantidad_c());
                values2.put("precio_ve", dt.getPrecio_ve());
                values2.put("importe_c", dt.getImporte_c());
                values2.put("idCompra", idCompra);
                values2.put("idProducto_c", dt.getProducto().getId());
                int cantT = dt.getCantidad_c() +Integer.parseInt(dt.getProducto().getExistencia_p()) ;
                modificarProducto(dt.getProducto().getId(), arre[k],cantT);

                idDetalle = db2.insert(TABLE_DETALLE_COMPRA, null, values2);
                k++;
            }


        }catch (Exception e){
            e.toString();
        }
        return idCompra;
    }

    public Compra buscarCompra(String clave){
        DBCompra dbCompra= new DBCompra(context);
        SQLiteDatabase  db = dbCompra.getWritableDatabase();
        Compra compra = null;
        Cursor cursor = null;
        int idPro=0;
        int idProducto=0;
        int idComr=0;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_COMPRAS + " WHERE clave_c = '"+ clave + "'", null);
        if (cursor.moveToFirst()){
            compra = new Compra();
            compra.setIdCompra(cursor.getInt(0));
            idComr=cursor.getInt(0);
            compra.setClave_c(cursor.getString(1));;
            compra.setFecha_c(cursor.getString(2));
            compra.setIVA(cursor.getFloat(3));
            compra.setSubtotal(cursor.getFloat(4));
            compra.setTotal(cursor.getFloat(5));
            Proveedor pr =new Proveedor();
            pr.setId(cursor.getInt(6));
            idPro = cursor.getInt(6);
            compra.setProveedor(pr);
        }

        DBProveedor dbProveedor= new DBProveedor(context);
        SQLiteDatabase  db1 = dbCompra.getWritableDatabase();
        Proveedor proveedor = null;
        Cursor cursor1 = null;

        cursor1 = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR + " WHERE idProveedor = '"+ idPro + "'", null);
        if (cursor1.moveToFirst()){
            proveedor = new Proveedor();
            proveedor.setId(cursor1.getInt(0));
            proveedor.setClave_pr(cursor1.getString(1));
            proveedor.setNombre_pr(cursor1.getString(2));
            proveedor.setCalle_pr(cursor1.getString(3));
            proveedor.setColonia_pr(cursor1.getString(4));
            proveedor.setCiudad_pr(cursor1.getString(5));
            proveedor.setRFC_pr(cursor1.getString(6));
            proveedor.setTelefono_pr(cursor1.getString(7));
            proveedor.setEmail_pr(cursor1.getString(8));
            proveedor.setSaldo_pr(cursor1.getInt(9));

        }
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();

        compra.setProveedor(proveedor);

        ArrayList<DetalleCompra> listaDC= new ArrayList<>();

        DetalleCompra detalleCompra = null;
        Producto producto = null;
        Cursor cursor2 = null;
        cursor2 = db3.rawQuery("SELECT * FROM " + TABLE_DETALLE_COMPRA + " WHERE idCompra = '"+ idComr + "'", null);
        if (cursor2.moveToFirst()){
            do {
                detalleCompra = new DetalleCompra();
                detalleCompra.setIdDetalleCompra(cursor2.getInt(0));
                detalleCompra.setClave_c(cursor2.getString(1));
                detalleCompra.setUnidad_c(cursor2.getString(2));
                detalleCompra.setCantidad_c(cursor2.getInt(3));
                detalleCompra.setPrecio_ve(cursor2.getFloat(4));
                detalleCompra.setImporte_c(cursor2.getFloat(5));

                producto = buscar(cursor2.getInt(7));
                detalleCompra.setProducto(producto);

                    listaDC.add(detalleCompra);


            }while(cursor2.moveToNext());
        }
        compra.setDetalleComprasA(listaDC);
        cursor.close();


        return  compra;

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
    public Proveedor buscarProveedor(int id){
        DBProveedor dbProveedor= new DBProveedor(context);
        SQLiteDatabase  db = dbProveedor.getWritableDatabase();
        Proveedor proveedor = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR + " WHERE idProveedor = '"+ id + "'", null);
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

    public boolean eliminarCompra(int clave){
        boolean eliminado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(" DELETE  from " + TABLE_COMPRAS +" where idCompra = '"+ clave+"';");
            eliminado = true;
        }catch (Exception e){
            e.toString();
        }
        return eliminado;
    }
    public boolean modificarProducto(int id,
                                     float precioPromedio_p, int cantidad){
        boolean modificado =false;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET precioPromedio_p = '"+precioPromedio_p+"', "   +  "existencia_p = '"+cantidad  + "' where idProducto = '"+id+"';");
            modificado = true;
        }catch (Exception e){
            e.toString();
        }
        return modificado;

    }




}
