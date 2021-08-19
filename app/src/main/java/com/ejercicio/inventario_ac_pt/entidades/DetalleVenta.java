package com.ejercicio.inventario_ac_pt.entidades;

public class DetalleVenta {
    int id;
    String clave_c;
    String unidad_ve;
    int cantidad_ve;
    float precio_ve;
    float importe_ve;
    int idProducto_ve;
    int idVenta_ve;
    Venta venta;
    Producto producto;

    public DetalleVenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave_c() {
        return clave_c;
    }

    public void setClave_c(String clave_c) {
        this.clave_c = clave_c;
    }

    public String getUnidad_ve() {
        return unidad_ve;
    }

    public void setUnidad_ve(String unidad_ve) {
        this.unidad_ve = unidad_ve;
    }

    public int getCantidad_ve() {
        return cantidad_ve;
    }

    public void setCantidad_ve(int cantidad_ve) {
        this.cantidad_ve = cantidad_ve;
    }

    public float getPrecio_ve() {
        return precio_ve;
    }

    public void setPrecio_ve(float precio_ve) {
        this.precio_ve = precio_ve;
    }

    public float getImporte_ve() {
        return importe_ve;
    }

    public void setImporte_ve(float importe_ve) {
        this.importe_ve = importe_ve;
    }

    public int getIdProducto_ve() {
        return idProducto_ve;
    }

    public void setIdProducto_ve(int idProducto_ve) {
        this.idProducto_ve = idProducto_ve;
    }

    public int getIdVenta_ve() {
        return idVenta_ve;
    }

    public void setIdVenta_ve(int idVenta_ve) {
        this.idVenta_ve = idVenta_ve;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
