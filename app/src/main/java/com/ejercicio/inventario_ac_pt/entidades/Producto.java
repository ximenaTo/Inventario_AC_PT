package com.ejercicio.inventario_ac_pt.entidades;

public class Producto {
    int id;
    String clave_p;
    String nombre_p ;
    String linea_p ;
    String existencia_p ;
    String unidad;
    int cantidad_ve;
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

    public Producto() {
    }

    public int getCantidad_ve() {
        return cantidad_ve;
    }

    public void setCantidad_ve(int cantidad_ve) {
        this.cantidad_ve = cantidad_ve;
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

    public String getClave_p() {
        return clave_p;
    }

    public void setClave_p(String clave_p) {
        this.clave_p = clave_p;
    }

    public String getNombre_p() {
        return nombre_p;
    }

    public void setNombre_p(String nombre_p) {
        this.nombre_p = nombre_p;
    }

    public String getLinea_p() {
        return linea_p;
    }

    public void setLinea_p(String linea_p) {
        this.linea_p = linea_p;
    }

    public String getExistencia_p() {
        return existencia_p;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setExistencia_p(String existencia_p) {
        this.existencia_p = existencia_p;
    }

    public float getPrecioCosto_p() {
        return precioCosto_p;
    }

    public void setPrecioCosto_p(float precioCosto_p) {
        this.precioCosto_p = precioCosto_p;
    }

    public float getPrecioPromedio_p() {
        return precioPromedio_p;
    }

    public void setPrecioPromedio_p(float precioPromedio_p) {
        this.precioPromedio_p = precioPromedio_p;
    }

    public float getPrecioVenta1_p() {
        return precioVenta1_p;
    }

    public void setPrecioVenta1_p(float precioVenta1_p) {
        this.precioVenta1_p = precioVenta1_p;
    }

    public float getPrecioventa2_p() {
        return precioventa2_p;
    }

    public void setPrecioventa2_p(float precioventa2_p) {
        this.precioventa2_p = precioventa2_p;
    }
}
