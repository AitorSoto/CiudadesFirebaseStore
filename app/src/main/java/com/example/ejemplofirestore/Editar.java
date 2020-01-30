package com.example.ejemplofirestore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Editar extends AppCompatActivity {
    private EditText nombre, pais, comunidad;
    private FirebaseFirestore db;
    private FloatingActionButton fab;
    private Ciudad c;
    DocumentReference citiesRef;
    ImageView imagen;
    private StorageReference mStorageRef;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private ProgressBar mProgressBar;

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
        mProgressBar = (ProgressBar)findViewById(R.id.progress_barEditar);
        String key = getIntent().getExtras().getString("Clave");
        citiesRef = db.collection("España").document(key);
        asignaFoto();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!camposRellenos())
                    Toast.makeText(getApplicationContext(), "No todos los campos estan rellenos", Toast.LENGTH_SHORT).show();
                else {
                    uploadImage();
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
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imagen.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {
        if (filePath != null) {
            // Defining the child of storageReference
            StorageReference ref = mStorageRef.getRoot().child("imagenes/"+nombre.getText().toString()+".jpg"); // nombre.getText().toString()+".jpg"

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    String nombreCiudad = nombre.getText().toString();
                    Toast.makeText(getApplicationContext(),"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                    c = new Ciudad(nombre.getText().toString(), comunidad.getText().toString(), pais.getText().toString(), "/imagenes/"+nombreCiudad+".jpg");
                    db.collection("España").document(nombre.getText().toString()).set(c);

                    Toast.makeText(getApplicationContext(), "Ciudad añadida", Toast.LENGTH_SHORT).show();
                    finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e){
                            // Error, Image not uploaded
                            Toast.makeText(getApplicationContext(),"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });

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
