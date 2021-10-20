package com.example.myapplication.todaysmovie.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.todaysmovie.R;
import com.example.myapplication.todaysmovie.adapter.MoviesListAdapter;
import com.example.myapplication.todaysmovie.adapter.OnMovieListener;
import com.example.myapplication.todaysmovie.pojo.MovieModel;
import com.example.myapplication.todaysmovie.utils.MoreDetailsActivity;
import com.example.myapplication.todaysmovie.viewmodels.MovieListViewModel;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    RecyclerView recyclerView;
    MoviesListAdapter moviesListAdapter;
    MovieListViewModel movieListViewModel;
     SearchView searchView;
    public static   boolean isPopular ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
            setupSearch();
            ObserveAnyChange();
            configurationRecyclerView();
            ObservePopularMovies();
           movieListViewModel.searchPopularMovieApi(1);
           isPopular = true;



    }

    private void ObservePopularMovies() {
        movieListViewModel.getPopularMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> mpopmovieModels) {
                // Observing for any data change
                if (mpopmovieModels!=null)
                {
                    for (MovieModel movieModel : mpopmovieModels)
                    {
                        moviesListAdapter.setmPopMoviesList(mpopmovieModels);
                    }


                }
            }
        });


    }


    public void ObserveAnyChange(){
        movieListViewModel.getMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if (movieModels!=null)
                {
                    for (MovieModel movieModel : movieModels)
                    {
                        moviesListAdapter.setmMoviesList(movieModels);
                    }


                }
            }
        });
    }


  private void searchMovieApi(String query,int pageNumber)
  {
      movieListViewModel.searchMovieApi(query,pageNumber);
  }

  private void configurationRecyclerView(){
        recyclerView = findViewById(R.id.recycler);
      moviesListAdapter = new MoviesListAdapter(this);
      recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
      recyclerView.setAdapter(moviesListAdapter);

      // RecyclerView Pagination
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              if (!recyclerView.canScrollVertically(1) && isPopular==false) {

                  movieListViewModel.searchNextPage();


              }
          }
      });
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              if (!recyclerView.canScrollVertically(1) && isPopular==true) {

                  movieListViewModel.searchNextPopularPage();

              }
          }
      });

  }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MoreDetailsActivity.class);
        intent.putExtra("movie",moviesListAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    public void setupSearch (){
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isPopular = false;
                movieListViewModel.searchMovieApi(query,1);

                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText)

            {

                return false;
            }




        });

searchView.setOnSearchClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }

});


searchView.setOnCloseListener(new SearchView.OnCloseListener() {
    @Override
    public boolean onClose() {
        isPopular = true;
        return false;
    }
});




    }
}