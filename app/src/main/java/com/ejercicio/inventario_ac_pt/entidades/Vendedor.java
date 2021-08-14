package com.ejercicio.inventario_ac_pt.entidades;

public class Vendedor {
    private int id;
    private String clave;
    private String nombre;
    private String calle;
    private String colonio;
    private String telefono;
    private String email;
    private int comision;

    public  Vendedor(){}

    public Vendedor(int id, String clave, String nombre, String calle, String colonio, String telefono, String email, int comision) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.calle = calle;
        this.colonio = colonio;
        this.telefono = telefono;
        this.email = email;
        this.comision = comision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonio() {
        return colonio;
    }

    public void setColonio(String colonio) {
        this.colonio = colonio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getComision() {
        return comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }




}
