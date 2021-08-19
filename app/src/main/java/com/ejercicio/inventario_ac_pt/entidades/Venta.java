package com.ejercicio.inventario_ac_pt.entidades;

import java.util.ArrayList;

public class Venta {
    int id;
    String clave_ve;
    String fecha_ve;
    String comision_ve;
    int idProducto_v;
    int idCliente_v;
    int cantidadT_ve;
    float IVA;
    float subtotal;
    float total;
    Cliente cliente;
    Producto producto;
    private ArrayList<DetalleVenta> detalleVentaA;

    public Venta() {
    }

    public ArrayList<DetalleVenta> getDetalleVentaA() {
        return detalleVentaA;
    }

    public void setDetalleVentaA(ArrayList<DetalleVenta> detalleVentaA) {
        this.detalleVentaA = detalleVentaA;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getIVA() {
        return IVA;
    }

    public void setIVA(float IVA) {
        this.IVA = IVA;
    }

    public int getCantidadT_ve() {
        return cantidadT_ve;
    }

    public void setCantidadT_ve(int cantidad_ve) {
        this.cantidadT_ve = cantidad_ve;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave_ve() {
        return clave_ve;
    }

    public void setClave_ve(String clave_ve) {
        this.clave_ve = clave_ve;
    }

    public String getFecha_ve() {
        return fecha_ve;
    }

    public void setFecha_ve(String fecha_ve) {
        this.fecha_ve = fecha_ve;
    }

    public String getComision_ve() {
        return comision_ve;
    }

    public void setComision_ve(String comision_ve) {
        this.comision_ve = comision_ve;
    }

    public int getIdProducto_v() {
        return idProducto_v;
    }

    public void setIdProducto_v(int idProducto_v) {
        this.idProducto_v = idProducto_v;
    }

    public int getIdCliente_v() {
        return idCliente_v;
    }

    public void setIdCliente_v(int idCliente_v) {
        this.idCliente_v = idCliente_v;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
