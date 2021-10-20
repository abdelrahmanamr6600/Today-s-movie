package com.example.myapplication.todaysmovie.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.todaysmovie.R;

import java.lang.reflect.InvocationTargetException;

public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // Widgets

    ImageView movie_image;
    RatingBar ratingBar ;
    OnMovieListener onMovieListener;



    public MoviesViewHolder(@NonNull View itemView , OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;

       ratingBar=itemView.findViewById(R.id.ratingBar);
        movie_image = itemView.findViewById(R.id.movie_image);
          itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
