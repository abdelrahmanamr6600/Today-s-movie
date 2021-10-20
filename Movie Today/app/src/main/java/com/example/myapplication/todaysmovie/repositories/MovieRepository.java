package com.example.myapplication.todaysmovie.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.todaysmovie.networkrequest.MovieApiClient;
import com.example.myapplication.todaysmovie.pojo.MovieModel;

import java.util.List;

public class MovieRepository {

     private static MovieRepository instance ;
     private MovieApiClient mMovieApiClient;
     private String mQuery;
     private int mPageNumber;

    public static MovieRepository getInstance(){
        if (instance==null){
            return instance = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository(){
       mMovieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMoviesList() {
        return mMovieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPopularMoviesList() {return mMovieApiClient.getPopularMovies();}


    public void searchMovieApi(String query,int pageNumber)
    {
        this.mQuery=query;
        this.mPageNumber = pageNumber;

        mMovieApiClient.searchMovieApi(query,pageNumber);

    }
    public void searchPopularMovieApi(int pageNumber){
        this.mPageNumber = mPageNumber;
        mMovieApiClient.searchMovieApiPopular(pageNumber);
    }

    public void searchMovieApiPop(int pageNumber)
    {

        mMovieApiClient.searchMovieApiPopular(pageNumber);

    }

    public void SearchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }
    public void SearchNextPopularPage(){
        searchPopularMovieApi(mPageNumber+1);
    }


}
