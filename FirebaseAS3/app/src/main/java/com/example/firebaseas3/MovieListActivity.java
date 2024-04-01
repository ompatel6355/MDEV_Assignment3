package com.example.firebaseas3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    RecyclerView movierecycleview;
    CustomAdapter Adapter;
    FloatingActionButton ftbtn;


    List<Movie> movieList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movierecycleview = findViewById(R.id.MovieRecycleView);
        movierecycleview.setLayoutManager(new LinearLayoutManager(this));
        Adapter =new CustomAdapter(this,movieList);
        movierecycleview.setAdapter(Adapter);



        FirebaseFirestore.getInstance().collection("mananpatel")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            movieList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie = document.toObject(Movie.class);
                                movie.setDocumentId(document.getId());
                                movieList.add(movie);
                            }
                            Adapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                });


        ftbtn = findViewById(R.id.floatbtn);
        ftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieListActivity.this, MovieAddActivity.class);
                startActivity(intent);

            }
        });




    }
}