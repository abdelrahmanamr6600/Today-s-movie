package com.example.myapplication.todaysmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.todaysmovie.R;

public class PopMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView movie_image;
    RatingBar ratingBar ;
    TextView release,language;

    OnMovieListener onMovieListener;
    public PopMoviesViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {

        super(itemView);
        ratingBar=itemView.findViewById(R.id.ratingBar_pop);
        movie_image = itemView.findViewById(R.id.movie_image_pop);
        language = itemView.findViewById(R.id.language);
        release = itemView.findViewById(R.id.release);

    this.onMovieListener = onMovieListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
