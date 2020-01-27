package com.example.ejemplofirestore;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Ciudad implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.comunidad);
        dest.writeString(this.pais);
        dest.writeParcelable((Parcelable) this.foto, flags);
    }

    protected Ciudad(Parcel in) {
        this.nombre = in.readString();
        this.comunidad = in.readString();
        this.pais = in.readString();
        this.foto = in.readParcelable(ImageView.class.getClassLoader());
    }

    public static final Parcelable.Creator<Ciudad> CREATOR = new Parcelable.Creator<Ciudad>() {
        @Override
        public Ciudad createFromParcel(Parcel source) {
            return new Ciudad(source);
        }

        @Override
        public Ciudad[] newArray(int size) {
            return new Ciudad[size];
        }
    };
}
