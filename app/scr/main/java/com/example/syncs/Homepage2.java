package com.example.syncs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;




public class Homepage2 extends AppCompatActivity {
    Button Logout, menu, Upload;
    TextView music01, music02, music03, music04;


    FirebaseAuth FirebaseAuth;
    private FirebaseAuth.AuthStateListener AuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);



        Logout = findViewById(R.id.idLogout2);
        menu = (Button) findViewById(R.id.button2);
        music01 = findViewById(R.id.textView9);
        music02 = findViewById(R.id.textView14);
        music03 = findViewById(R.id.textView15);
        music04 = findViewById(R.id.textView16);
        Upload = findViewById(R.id.idUpload);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Toast.makeText(Homepage2.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Homepage2.this, Login.class);
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Menu.class);
                startActivity(intent);
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Upload.class);
                startActivity(intent);
            }
        });


        music01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Music.class);
                startActivity(intent);
            }
        });

        music02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Music02.class);
                startActivity(intent);
            }
        });
        music03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Music03.class);
                startActivity(intent);
            }
        });
        music04.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this, Music04.class);
                startActivity(intent);
            }
        });












    }
}







