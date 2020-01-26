package com.example.ejemplofirestore;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.IOException;

public class CiudadAdapter extends FirestoreRecyclerAdapter<Ciudad, Holder> implements View.OnClickListener, View.OnLongClickListener {
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public CiudadAdapter(@NonNull FirestoreRecyclerOptions<Ciudad> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Ciudad model) {
        try {
            holder.bind(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new Holder(view);
    }

    void onClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    void onLongClickListener(View.OnLongClickListener listenerLong){
        this.listenerLong = listenerLong;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) listener.onClick(v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (listenerLong!=null){
            listenerLong.onLongClick(v);
        }
        return true;
    }
}
