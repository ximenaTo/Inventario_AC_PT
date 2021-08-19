package com.ejercicio.inventario_ac_pt.entidades;

public class Cliente {

    int id;
    String clave_c;
    String nombre_c;
    String calle_c;
    String colonia_c;
    String ciudad_c;
    String RFC_c;
    String telefono_c;
    String email_c;
    float saldo_c;

    public Cliente() {
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

    public String getNombre_c() {
        return nombre_c;
    }

    public void setNombre_c(String nombre_c) {
        this.nombre_c = nombre_c;
    }

    public String getCalle_c() {
        return calle_c;
    }

    public void setCalle_c(String calle_c) {
        this.calle_c = calle_c;
    }

    public String getColonia_c() {
        return colonia_c;
    }

    public void setColonia_c(String colonia_c) {
        this.colonia_c = colonia_c;
    }

    public String getCiudad_c() {
        return ciudad_c;
    }

    public void setCiudad_c(String ciudad_c) {
        this.ciudad_c = ciudad_c;
    }

    public String getRFC_c() {
        return RFC_c;
    }

    public void setRFC_c(String RFC_c) {
        this.RFC_c = RFC_c;
    }

    public String getTelefono_c() {
        return telefono_c;
    }

    public void setTelefono_c(String telefono_c) {
        this.telefono_c = telefono_c;
    }

    public String getEmail_c() {
        return email_c;
    }

    public void setEmail_c(String email_c) {
        this.email_c = email_c;
    }

    public float getSaldo_c() {
        return saldo_c;
    }

    public void setSaldo_c(float saldo_c) {
        this.saldo_c = saldo_c;
    }
}
