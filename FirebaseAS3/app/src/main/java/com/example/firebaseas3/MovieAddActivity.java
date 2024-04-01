package com.example.firebaseas3;

import static com.google.common.io.Files.getFileExtension;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class MovieAddActivity extends AppCompatActivity {

    FirebaseFirestore firestoreobj;
    String docId = null;
    Button btn_add_edit, btn_image_upload, btnback;
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    Uri imageUri;
    String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_add);
        firestoreobj = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

       btn_image_upload= findViewById(R.id.btnaddthumbnail);
        btn_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


       btn_add_edit= findViewById(R.id.btn_add_edit_movie);
        btn_add_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_movies();
            }
        });
        btnback= findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieAddActivity.this, MovieListActivity.class);
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImage();
        }
    }


    private void add_movies() {
        String title = ((EditText) findViewById(R.id.title_add)).getText().toString();
        String studio = ((EditText) findViewById(R.id.studio_add)).getText().toString();
        String thumbnailUrl = ((TextView) findViewById(R.id.thumburl)).getText().toString();
        String rating = ((EditText) findViewById(R.id.rating_add)).getText().toString();

        Map<String, Object> movie = new HashMap<>();
        movie.put("movietitle", title);
        movie.put("moviestudio", studio);
        movie.put("moviethumb", thumbnailUrl);
        movie.put("movierating", rating);


        if (docId == null) {
            firestoreobj.collection("mananpatel").add(movie)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MovieAddActivity.this, "Movie saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MovieAddActivity.this, "Error saving movie", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(String.valueOf(imageUri)));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
                            downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    ((TextView) findViewById(R.id.thumburl)).setText(imageUrl);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MovieAddActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}