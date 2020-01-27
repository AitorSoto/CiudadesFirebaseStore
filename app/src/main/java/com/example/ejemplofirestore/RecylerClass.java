package com.example.ejemplofirestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.MapMaker;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.ThrowOnExtraProperties;


public class RecylerClass extends AppCompatActivity {
    private CiudadAdapter adapter;
    private Holder holder;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private Query query;
    private FirebaseFirestore firebaseFirestore;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        fab = (FloatingActionButton)findViewById(R.id.fabRecycler);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecylerClass.this, Agregar.class);
                startActivity(i);
            }
        });
        cargaRecycler();
        adapter.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecylerClass.this, Editar.class);
                int pos = recyclerView.getChildAdapterPosition(v);
                final String key = adapter.getSnapshots().getSnapshot(pos).getId();
                Ciudad ciudad = adapter.getItem(pos);


                i.putExtra("Clave", key);
                i.putExtra("Ciudad", ciudad);
                startActivity(i);
            }
        });
        adapter.onLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecylerClass.this);
                final View view = v;
                builder.setMessage("¿Quieres eliminar esta ciudad?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String key = adapter.getSnapshots().getSnapshot(recyclerView.getChildAdapterPosition(view)).getId();
                                firebaseFirestore.collection("España").document(key).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RecylerClass.this, "Se ha elimnado el registro", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RecylerClass.this, "Fallo al eliminar", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });
    }

    private void cargaRecycler(){
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();

        query = firebaseFirestore.collection("España").orderBy("nombre");

        FirestoreRecyclerOptions<Ciudad> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Ciudad>().setQuery(query, Ciudad.class).build();

        adapter = new CiudadAdapter(firestoreRecyclerOptions);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart()
    {
        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        adapter.stopListening();
        super.onStop();
    }
}
