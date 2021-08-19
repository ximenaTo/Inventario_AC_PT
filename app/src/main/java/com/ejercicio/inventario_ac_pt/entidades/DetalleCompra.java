package com.ejercicio.inventario_ac_pt.entidades;

public class DetalleCompra {

    private int idDetalleCompra;
    private String clave_c;
    private String unidad_c;
    private int cantidad_c;
    private float precio_ve;
    private float importe_c;
    private Compra compra;
    private Producto producto;

    public DetalleCompra(){}

    public DetalleCompra(int idDetalleCompra, String clave_c, String unidad_c, int cantidad_c, float precio_ve, float importe_c, Compra compra, Producto producto) {
        this.idDetalleCompra = idDetalleCompra;
        this.clave_c = clave_c;
        this.unidad_c = unidad_c;
        this.cantidad_c = cantidad_c;
        this.precio_ve = precio_ve;
        this.importe_c = importe_c;
        this.compra = compra;
        this.producto = producto;
    }

    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public String getClave_c() {
        return clave_c;
    }

    public void setClave_c(String clave_c) {
        this.clave_c = clave_c;
    }

    public String getUnidad_c() {
        return unidad_c;
    }

    public void setUnidad_c(String unidad_c) {
        this.unidad_c = unidad_c;
    }

    public int getCantidad_c() {
        return cantidad_c;
    }

    public void setCantidad_c(int cantidad_c) {
        this.cantidad_c = cantidad_c;
    }

    public float getPrecio_ve() {
        return precio_ve;
    }

    public void setPrecio_ve(float precio_ve) {
        this.precio_ve = precio_ve;
    }

    public float getImporte_c() {
        return importe_c;
    }

    public void setImporte_c(float importe_c) {
        this.importe_c = importe_c;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }





}
