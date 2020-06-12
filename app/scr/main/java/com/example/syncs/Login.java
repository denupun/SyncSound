package com.example.syncs;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText Email, Password;
    Button Login;
    private int counter = 5;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button Admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.idEmail);
        Password = findViewById(R.id.idPassword);
        Login = findViewById(R.id.idLogin);
        Admin = findViewById(R.id.idAdmin);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Homepage1.class);
                    startActivity(i);
                }

            }

        };

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String pwd = Password.getText().toString();
                if (email.isEmpty()) {
                    Email.setError("Please enter Email Address");
                    Email.requestFocus();
                } else if (pwd.isEmpty()) {
                    Password.setError("Please enter your Password");
                    Password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(Login.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Error! Invalid email or password", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(Login.this, Homepage1.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String pwd = Password.getText().toString();

                if ((email.equals("Admin")) && (pwd.equals("1234"))) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Homepage2.class);
                    startActivity(intent);
                } else {
                    counter--;
                    if (counter >= 1) {
                        Toast.makeText(Login.this, "Log in Error, Invalid Email or Password!", Toast.LENGTH_SHORT).show();
                    }

                    if (counter == 0) {
                        Admin.setEnabled(false);
                        Toast.makeText(Login.this, "Login Blocked, Try again later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}








