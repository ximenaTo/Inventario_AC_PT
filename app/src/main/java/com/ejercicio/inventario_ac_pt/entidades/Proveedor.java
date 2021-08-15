package com.ejercicio.inventario_ac_pt.entidades;

public class Proveedor {
    int id;
    String clave_pr;
    String nombre_pr;
    String calle_pr;
    String colonia_pr;
    String ciudad_pr;
    String RFC_pr;
    String telefono_pr;
    String email_pr;
    float saldo_pr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave_pr() {
        return clave_pr;
    }

    public void setClave_pr(String clave_pr) {
        this.clave_pr = clave_pr;
    }

    public String getNombre_pr() {
        return nombre_pr;
    }

    public void setNombre_pr(String nombre_pr) {
        this.nombre_pr = nombre_pr;
    }

    public String getCalle_pr() {
        return calle_pr;
    }

    public void setCalle_pr(String calle_pr) {
        this.calle_pr = calle_pr;
    }

    public String getColonia_pr() {
        return colonia_pr;
    }

    public void setColonia_pr(String colonia_pr) {
        this.colonia_pr = colonia_pr;
    }

    public String getCiudad_pr() {
        return ciudad_pr;
    }

    public void setCiudad_pr(String ciudad_pr) {
        this.ciudad_pr = ciudad_pr;
    }

    public String getRFC_pr() {
        return RFC_pr;
    }

    public void setRFC_pr(String RFC_pr) {
        this.RFC_pr = RFC_pr;
    }

    public String getTelefono_pr() {
        return telefono_pr;
    }

    public void setTelefono_pr(String telefono_pr) {
        this.telefono_pr = telefono_pr;
    }

    public String getEmail_pr() {
        return email_pr;
    }

    public void setEmail_pr(String email_pr) {
        this.email_pr = email_pr;
    }

    public float getSaldo_pr() {
        return saldo_pr;
    }

    public void setSaldo_pr(float saldo_pr) {
        this.saldo_pr = saldo_pr;
    }
}
