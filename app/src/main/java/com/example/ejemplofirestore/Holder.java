package com.example.ejemplofirestore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Holder extends RecyclerView.ViewHolder  {
    TextView ciudad, pais;
    ImageView foto;

    public Holder(View v) {
        super(v);
        ciudad=(TextView) v.findViewById(R.id.ciudad);
        pais=(TextView) v.findViewById(R.id.pais);
        foto = (ImageView)v.findViewById(R.id.foto);
    }

    public void bind(Ciudad item) throws IOException {

        ciudad.setText(item.getNombre() );
        pais.setText(item.getPais());

    }
}