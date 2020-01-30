package com.example.ejemplofirestore;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

        StorageReference ref = FirebaseStorage.getInstance().getReference(item.getFoto());

        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Picasso.get().load(task.getResult()).into(foto);
            }
        });
    }
}