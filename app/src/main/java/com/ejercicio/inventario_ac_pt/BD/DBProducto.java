package com.ejercicio.inventario_ac_pt.BD;

import android.content.Context;

import androidx.annotation.Nullable;

public class DBProducto extends DBHelper {
    Context context;

    public DBProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }
}
