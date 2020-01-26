package com.example.ejemplofirestore;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Ciudad {
    private String nombre;
    private String comunidad;
    private String pais;
    private ImageView foto;

    public Ciudad() {
    }

    public Ciudad(String nombre, String comunidad, String pais, ImageView foto) {
        this.nombre = nombre;
        this.comunidad = comunidad;
        this.pais = pais;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "nombre='" + nombre + '\'' +
                ", comunidad='" + comunidad + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
