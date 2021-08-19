package com.ejercicio.inventario_ac_pt.entidades;

public class PrecioProm {
    private String clave_p;
    private Float precio_p;

    public PrecioProm(String clave_p, Float precio_p) {
        this.clave_p = clave_p;
        this.precio_p = precio_p;
    }
    public  PrecioProm(){}




    public String getClave_p() {
        return clave_p;
    }

    public void setClave_p(String clave_p) {
        this.clave_p = clave_p;
    }

    public Float getPrecio_p() {
        return precio_p;
    }

    public void setPrecio_p(Float precio_p) {
        this.precio_p = precio_p;
    }



}
