package com.ejercicio.inventario_ac_pt.entidades;

public class Producto {
    int id;
    String clave_p;
    String nombre_p ;
    String linea_p ;
    String existencia_p ;
    float precioCosto_p ;
    float precioPromedio_p ;
    float precioVenta1_p;
    float precioventa2_p ;

    public Producto(String clave_p, String nombre_p, String linea_p, String existencia_p, float precioCosto_p, float precioPromedio_p, float precioVenta1_p, float precioventa2_p) {
        this.clave_p = clave_p;
        this.nombre_p = nombre_p;
        this.linea_p = linea_p;
        this.existencia_p = existencia_p;
        this.precioCosto_p = precioCosto_p;
        this.precioPromedio_p = precioPromedio_p;
        this.precioVenta1_p = precioVenta1_p;
        this.precioventa2_p = precioventa2_p;
    }

    public Producto(int id, String clave_p, String nombre_p, String linea_p, String existencia_p, float precioCosto_p, float precioPromedio_p, float precioVenta1_p, float precioventa2_p) {
        this.id = id;
        this.clave_p = clave_p;
        this.nombre_p = nombre_p;
        this.linea_p = linea_p;
        this.existencia_p = existencia_p;
        this.precioCosto_p = precioCosto_p;
        this.precioPromedio_p = precioPromedio_p;
        this.precioVenta1_p = precioVenta1_p;
        this.precioventa2_p = precioventa2_p;
    }

    public Producto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
