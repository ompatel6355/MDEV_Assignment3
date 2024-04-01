package com.example.firebaseas3;

import static com.google.common.io.Files.getFileExtension;

import androidx.annotation.NonNull;
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

public class EditMovieActivity extends AppCompatActivity {
    FirebaseFirestore firestoreobj1;
    String docId1 = null;
    Button btn_edit, btn_image_upload1, btnback1;
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storRef1;
    Uri imageUri;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);


        firestoreobj1 = FirebaseFirestore.getInstance();
        storRef1 = FirebaseStorage.getInstance().getReference("uploads");
        docId1 = getIntent().getStringExtra("documentId");


        btn_image_upload1= findViewById(R.id.btneditthumbnail);
        btn_image_upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnback1= findViewById(R.id.btn_back_editscreen);
        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditMovieActivity.this, MovieListActivity.class);
                startActivity(intent);

            }
        });

        btn_edit= findViewById(R.id.btn_edit_movie);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_movies();
            }
        });

            firestoreobj1.collection("mananpatel").document(docId1).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot document) {
                            if (document.exists()) {

                                EditText titleEditText = findViewById(R.id.title_edit);
                                EditText studioEditText = findViewById(R.id.studio_edit);
                                TextView thumbUrlTextView = findViewById(R.id.editthumburl);
                                EditText ratingEditText = findViewById(R.id.rating_edit);


                                titleEditText.setText(document.getString("movietitle"));
                                studioEditText.setText(document.getString("moviestudio"));
                                thumbUrlTextView.setText(document.getString("moviethumb"));
                                ratingEditText.setText(document.getString("movierating"));

                            }
                        }
                    });
    }
    private void edit_movies() {
        String title = ((EditText) findViewById(R.id.title_edit)).getText().toString();
        String studio = ((EditText) findViewById(R.id.studio_edit)).getText().toString();
        String thumbnailUrl = ((TextView) findViewById(R.id.editthumburl)).getText().toString();
        String rating = ((EditText) findViewById(R.id.rating_edit)).getText().toString();

        Map<String, Object> movie = new HashMap<>();
        movie.put("movietitle", title);
        movie.put("moviestudio", studio);
        movie.put("moviethumb", thumbnailUrl);
        movie.put("movierating", rating);


            firestoreobj1.collection("mananpatel").document(docId1).set(movie)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditMovieActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditMovieActivity.this, "Error updating movie", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storRef1.child(System.currentTimeMillis()
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
                                    ((TextView) findViewById(R.id.editthumburl)).setText(imageUrl);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditMovieActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
