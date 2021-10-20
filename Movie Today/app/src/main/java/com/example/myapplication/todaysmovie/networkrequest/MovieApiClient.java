package com.example.myapplication.todaysmovie.networkrequest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.todaysmovie.AppExecutors;
import com.example.myapplication.todaysmovie.pojo.MovieModel;
import com.example.myapplication.todaysmovie.response.MovieSearchResponse;
import com.example.myapplication.todaysmovie.utils.CredentialsModel;
import com.google.gson.internal.$Gson$Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private static MovieApiClient instance;
    //for search
    private MutableLiveData<List<MovieModel>> mMoviesList ;
    //for popularMovies
    private MutableLiveData<List<MovieModel>> mPopularMovieList;

    //making Global  Runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    //making Popular runnable
    private RetrieveMoviesRunnablePop retrievePopularMoviesRunnable;

       public static MovieApiClient getInstance(){
           if (instance==null)
           {
               instance = new MovieApiClient();
           }
           return instance;
       }


       private MovieApiClient(){
           mMoviesList = new MutableLiveData<>();
           mPopularMovieList = new MutableLiveData<>();
       }


  public LiveData<List<MovieModel>> getMovies(){
           return mMoviesList;
  }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return mPopularMovieList;
    }

  public void searchMovieApi( String query,int pageNumber){
          if (retrieveMoviesRunnable!=null){
              retrieveMoviesRunnable = null;
          }
          retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);

           final Future MyHandler = AppExecutors.getInstance().networkIo().submit(retrieveMoviesRunnable);

           AppExecutors.getInstance().networkIo().schedule(new Runnable() {
               @Override
               public void run() {
                     // Canceling the Retrofit call
                   MyHandler.cancel(true);
               }
           },3000, TimeUnit.MILLISECONDS);
  }

    public void searchMovieApiPopular(int pageNumber){
        if (retrievePopularMoviesRunnable!=null){
            retrievePopularMoviesRunnable = null;
        }
        retrievePopularMoviesRunnable = new RetrieveMoviesRunnablePop(pageNumber);

        final Future MyHandler2 = AppExecutors.getInstance().networkIo().submit(retrievePopularMoviesRunnable);

        AppExecutors.getInstance().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                // Canceling the Retrofit call
                MyHandler2.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }



    // Retreving data from RestApi bt runnable class
//We Have 2 types of Queries : ID@ search Query

  private class RetrieveMoviesRunnable implements Runnable{
           private String query;
           private int pageNumber;
           boolean cancelRequest;

     public RetrieveMoviesRunnable(String query, int page) {
         this.query = query;
         this.pageNumber = page;
         cancelRequest = false;
     }

     @Override
      public void run() {

         try {
             Response response = getMovies(query,pageNumber).execute();

              if (cancelRequest)
              {
                  return;
              }
              if (response.code()==200)
              {
                  List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                  if (pageNumber==1){
                      //sending data to Live data
                      //post value for background thread
                      mMoviesList.postValue(list);
                      Log.v("size", String.valueOf(list.size()));
                  }else {
                      List<MovieModel> currentMovies = mMoviesList.getValue();
                      currentMovies.addAll(list);
                      mMoviesList.postValue(currentMovies);

                  }
              }else {

                  String error = response.errorBody().toString();
                  Log.v("Tag","Error response"+error);
                  mMoviesList.postValue(null);
              }

         } catch (IOException e) {
             e.printStackTrace();
             mMoviesList.postValue(null);
         }


      }
     // Search Method
     private Call<MovieSearchResponse> getMovies(String query,int pageNumber)
     {
         return Service.getApiInterface().searchMovie(
                 CredentialsModel.api_Key
                 ,query,
                 pageNumber);

     }

     private void CancelRequest(){
         Log.v("Tag","Cancelling Request");
         cancelRequest = true;
     }

 }

    private class RetrieveMoviesRunnablePop implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int page) {

            this.pageNumber = page;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response2 = getPop(pageNumber).execute();

                if (cancelRequest)
                {
                    return;
                }
                if (response2.code()==200)
                {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if (pageNumber==1){
                        //sending data to Live data
                        //post value for background thread
                        mPopularMovieList.postValue(list);

                    }else {
                        List<MovieModel> currentMovies = mPopularMovieList.getValue();
                        currentMovies.addAll(list);
                        mPopularMovieList.postValue(currentMovies);


                    }
                }else {

                    String error = response2.errorBody().toString();
                    Log.v("Tag","Error response 2"+error);
                    mPopularMovieList.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mPopularMovieList.postValue(null);
            }


        }
        // Search Method
        private Call<MovieSearchResponse> getPop(int pageNumber)
        {
            return Service.getApiInterface().getPopularMovie(
                    CredentialsModel.api_Key
                    ,
                    pageNumber);

        }

        private void CancelRequest(){
            Log.v("Tag","Cancelling Request");
            cancelRequest = true;
        }

    }






}
