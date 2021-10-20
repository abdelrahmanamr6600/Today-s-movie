package com.example.myapplication.todaysmovie.data;

import com.example.myapplication.todaysmovie.pojo.MovieModel;
import com.example.myapplication.todaysmovie.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovie(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(@Path("movie_id") String id ,@Query("api_key") String key);

}
