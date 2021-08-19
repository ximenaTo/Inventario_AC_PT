package com.ejercicio.inventario_ac_pt.entidades;

import java.util.ArrayList;

public class Compra {
    private int idCompra;
    private String clave_c;
    private String fecha_c;
    private float IVA;
    private float subtotal;
    private float total;
    private Proveedor proveedor;
    private Vendedor vendedor;
    private ArrayList<DetalleCompra> detalleComprasA;

    public Compra(){}

    public Compra(int idCompra, String clave_c, String fecha_c, float IVA, float subtotal, float total, Proveedor proveedor, Vendedor vendedor, ArrayList<DetalleCompra> detalleComprasA) {
        this.idCompra = idCompra;
        this.clave_c = clave_c;
        this.fecha_c = fecha_c;
        this.IVA = IVA;
        this.subtotal = subtotal;
        this.total = total;
        this.proveedor = proveedor;
        this.vendedor = vendedor;
        this.detalleComprasA = detalleComprasA;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getClave_c() {
        return clave_c;
    }

    public void setClave_c(String clave_c) {
        this.clave_c = clave_c;
    }

    public String getFecha_c() {
        return fecha_c;
    }

    public void setFecha_c(String fecha_c) {
        this.fecha_c = fecha_c;
    }

    public float getIVA() {
        return IVA;
    }

    public void setIVA(float IVA) {
        this.IVA = IVA;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public ArrayList<DetalleCompra> getDetalleComprasA() {
        return detalleComprasA;
    }

    public void setDetalleComprasA(ArrayList<DetalleCompra> detalleComprasA) {
        this.detalleComprasA = detalleComprasA;
    }






}
