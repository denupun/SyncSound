package com.example.syncs;

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
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText Username, Password, Email;
    Button Signup;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    mFirebaseAuth = FirebaseAuth.getInstance();
    Email = findViewById(R.id.idEmail);
    Password= findViewById(R.id.idPassword);
    Username = findViewById(R.id.idUsername);
    Signup = findViewById(R.id.idSignup);

        FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Register.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Register.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
    Signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = Email.getText().toString();
            String pwd = Password.getText().toString();
            if(email.isEmpty()) {
                Email.setError("Please enter email address");
                Email.requestFocus();
            }
            else if(pwd.isEmpty()){
                Password.setError("Please enter your Password");
                Password.requestFocus();

            }
            else {
                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {


                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(Register.this,"Sign Up unsuccessful, Please enter correct email address",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(Register.this,Login.class));
                                }
                            }
                        });
                    }
      }
    });
    }
}



