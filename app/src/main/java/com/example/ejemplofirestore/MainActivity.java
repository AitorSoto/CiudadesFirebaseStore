package com.example.ejemplofirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.RecursiveAction;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText user;
    private EditText pasword;
    private Button sendButton;
    private Button btCrear;
    private Button iniciarSinAut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.user);
        pasword = (EditText) findViewById(R.id.password);
        sendButton = (Button) findViewById(R.id.log);
        btCrear = (Button) findViewById(R.id.register);
        iniciarSinAut = (Button) findViewById(R.id.iniciarSinAut);
        iniciarSinAut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecylerClass.class);
                startActivity(i);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        /////Iniciar sesión con usuario y contraseña
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(user.getText().toString(), pasword.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed:" + task.getException(), Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(MainActivity.this, "Todo bien señor", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MainActivity.this,RecylerClass.class);
                                    startActivity(intent);// Activity is started with requestCode 2
                                }

                            }
                        });
            }
        });

        ////// Crear usuario nueveo y iniciar sesión
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(user.getText().toString(), pasword.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(MainActivity.this, "Problemas al crear usuario" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
