package com.example.ejemplofirestore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Editar extends AppCompatActivity {
    private EditText nombre, pais, comunidad;
    private String id;
    private FirebaseFirestore db;
    private FloatingActionButton fab;
    private Ciudad c;
    DocumentReference citiesRef;
    ImageView imagen;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        db =  FirebaseFirestore.getInstance();
        c = getIntent().getExtras().getParcelable("Ciudad");
        mStorageRef = FirebaseStorage.getInstance().getReference(c.getFoto());
        imagen = (ImageView)findViewById(R.id.imagenEditar);
        nombre = (EditText)findViewById(R.id.nombreEditar);
        pais = (EditText)findViewById(R.id.paisEditar);
        comunidad = (EditText)findViewById(R.id.comunidadEditar);
        fab = (FloatingActionButton)findViewById(R.id.fabEditar);
        String key = getIntent().getExtras().getString("Clave");
        citiesRef = db.collection("España").document(key);


        asignaFoto();
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
        asignaTextoCampos();
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }

    private void asignaTextoCampos(){
        nombre.setText(c.getNombre());
        comunidad.setText(c.getComunidad());
        pais.setText(c.getPais());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean camposRellenos(){
        return !comunidad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
        }
    }

    private void asignaFoto(){
        mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Picasso.get().load(task.getResult()).into(imagen);
            }
        });
    }
}
