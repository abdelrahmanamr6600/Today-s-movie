package com.example.myapplication.todaysmovie.response;

import com.example.myapplication.todaysmovie.pojo.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//this class for single movie request
public class MovieResponse {
    // finding the movie object
    @SerializedName("result")
    @Expose
     private MovieModel movieModel ;

    public MovieModel getMovieModel (){
        return movieModel;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
