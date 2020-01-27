package com.example.ejemplofirestore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Editar extends AppCompatActivity {
    private EditText nombre, pais, comunidad;
    private String id;
    private FirebaseFirestore db;
    private FloatingActionButton fab;
    private Ciudad c;
    DocumentReference citiesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        id = getIntent().getStringExtra("id");
        db =  FirebaseFirestore.getInstance();
        db.collection("España");
        nombre = (EditText)findViewById(R.id.nombreEditar);
        pais = (EditText)findViewById(R.id.paisEditar);
        comunidad = (EditText)findViewById(R.id.comunidadEditar);
        fab = (FloatingActionButton)findViewById(R.id.fabEditar);
        citiesRef = db.collection("España").document(id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!camposRellenos())
                    Toast.makeText(getApplicationContext(), "No todos los campos estan rellenos", Toast.LENGTH_SHORT).show();
                else {
                    citiesRef.set(new Ciudad(nombre.getText().toString(), comunidad.getText().toString(), pais.getText().toString(), null));
                    finish();
                }
            }
        });

    }

    private void añadeInformacionCampos(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean camposRellenos(){
        return !comunidad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty();
    }
}
