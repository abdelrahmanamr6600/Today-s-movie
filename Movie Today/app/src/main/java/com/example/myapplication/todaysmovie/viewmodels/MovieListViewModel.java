package com.example.myapplication.todaysmovie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.todaysmovie.pojo.MovieModel;
import com.example.myapplication.todaysmovie.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    //this class used foe viewModel
   MovieRepository mMoviesRepository = MovieRepository.getInstance();

    public MovieListViewModel() {
    }
    public LiveData<List<MovieModel>> getMovie(){
        return mMoviesRepository.getMoviesList();
    }

    public LiveData<List<MovieModel>> getPopularMovie(){ return mMoviesRepository.getPopularMoviesList(); }

    public void searchMovieApi(String query,int pageNumber){
        mMoviesRepository.searchMovieApi(query,pageNumber);
    }
    public void searchPopularMovieApi(int pageNumber){
        mMoviesRepository.searchMovieApiPop(pageNumber);
    }


    public void searchNextPage(){
        mMoviesRepository.SearchNextPage();
    }
    public void searchNextPopularPage(){
        mMoviesRepository.SearchNextPopularPage();
    }
}
