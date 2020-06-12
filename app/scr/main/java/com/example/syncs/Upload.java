package com.example.syncs;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;


public class Upload extends AppCompatActivity {

    Button Choose, Upload;
    TextView notification;
    Uri audioUri;


    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        Choose = findViewById(R.id.selectFile);
        Upload = findViewById(R.id.Upload);
        notification = findViewById(R.id.notification);

        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    selectAudio();
                } else
                    ActivityCompat.requestPermissions(Upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

            }

        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioUri != null)
                    UploadAudio(audioUri);
                else
                    Toast.makeText(Upload.this, "Select an audio..", Toast.LENGTH_SHORT).show();
            }
        });
    }

            private void UploadAudio(Uri audioUri) {

            progressDialog= new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Uploading...");
            progressDialog.setProgress(0);
            progressDialog.show();

            final String fileName=System.currentTimeMillis()+"";
                StorageReference storageReference= storage.getReference();

                storageReference.child("Uploads").child(fileName).putFile(audioUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){


                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)


                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String url= Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString();
                                DatabaseReference reference = database.getReference();

                                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful())
                                          Toast.makeText(Upload.this, "Audio Successfully Uploaded..",Toast.LENGTH_SHORT).show();
                                      else
                                          Toast.makeText(Upload.this, "Upload Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload.this, "Upload Failed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress=(int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgress);
                    }
                });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
       {
           selectAudio();
       }
    else
           Toast.makeText(Upload.this, "Please Provide Permission..", Toast.LENGTH_SHORT).show();


    }

    private void selectAudio(){
        Intent intent=new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            audioUri = data.getData();
            notification.setText("Audio is selected: " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(Upload.this, "Please select an Audio File", Toast.LENGTH_SHORT).show();
        }

    }

}



