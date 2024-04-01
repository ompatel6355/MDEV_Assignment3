package com.example.firebaseas3;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> ml;

    public CustomAdapter(Context context, List<Movie> ml) {
        this.context = context;
        this.ml = ml;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_movie_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = ml.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return ml.size();
    }



    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movieThumb;
        TextView movieTitle;
        TextView movieStudio;
        TextView movieRating;
        Button editbtn;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumb = itemView.findViewById(R.id.moviethumb);
            movieTitle = itemView.findViewById(R.id.titlemovie);
            movieStudio = itemView.findViewById(R.id.Studiomovie);
            movieRating = itemView.findViewById(R.id.ratingmovie);
            editbtn =itemView.findViewById(R.id.editbtn);

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Movie movie = ml.get(position);
                        String documentId = movie.getDocumentId();
                        Intent intent = new Intent(context, EditMovieActivity.class);
                        intent.putExtra("documentId", documentId);
                        context.startActivity(intent);

                    }
                }
            });
        }




        public void bind(Movie movie) {
            movieTitle.setText(movie.getMovietitle());
            movieStudio.setText(movie.getMoviestudio());
            movieRating.setText(movie.getMovierating());
            Glide.with(context)
                    .load(movie.getMoviethumb())
                    .override(200, 200) // Resize image to 200x200 pixels
                    .into(movieThumb);
        }
    }
}

