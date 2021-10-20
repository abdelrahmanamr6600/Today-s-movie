package com.example.myapplication.todaysmovie.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.todaysmovie.R;
import com.example.myapplication.todaysmovie.pojo.MovieModel;

public class MoreDetailsActivity extends AppCompatActivity {
    TextView title, overView;
    RatingBar ratingBar;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        title=findViewById(R.id.title_tv);
        overView =findViewById(R.id.textView_over_view);
        imageView = findViewById(R.id.imageView);
        ratingBar = findViewById(R.id.ratingbar);
        getDataFromIntent();

    }

   private void  getDataFromIntent(){
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            title.setText(movieModel.getTitle());
            overView.setText(movieModel.getOverview());
            ratingBar.setRating(movieModel.getVote_average()/2);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+movieModel.getPoster_path()).into(imageView);
        }




   }
}