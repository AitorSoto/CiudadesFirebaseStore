package com.example.ejemplofirestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class Agregar extends AppCompatActivity {
    FloatingActionButton fab;
    FirebaseFirestore db;
    EditText comunidad, pais, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar);
        db = FirebaseFirestore.getInstance();
        db.collection("España");
        comunidad = (EditText)findViewById(R.id.comunidad);
        pais = (EditText)findViewById(R.id.pais);
        nombre = (EditText)findViewById(R.id.nombre);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposRellenos()){
                    Ciudad c = new Ciudad(nombre.getText().toString(), comunidad.getText().toString(), pais.getText().toString(), null);
                    db.collection("España").document(nombre.getText().toString()).set(c);
                    Toast.makeText(getApplicationContext(), "Ciudad añadida", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No todos los campos estan rellenos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean camposRellenos(){
        return !comunidad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
