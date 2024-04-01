package com.example.firebaseas3;

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

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText usernameregister, passwordregister;
    Button btnregisteruser, btngotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        usernameregister= findViewById(R.id.username_register);
        passwordregister = findViewById(R.id.password_register);
        btnregisteruser = findViewById(R.id.btnregisteruser);
        btngotologin = findViewById(R.id.btngotoLogin);


        btnregisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = usernameregister.getText().toString();
                String password = passwordregister.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Check Details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btngotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}