package com.example.myapplication.todaysmovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.todaysmovie.utils.CredentialsModel;
import com.example.myapplication.todaysmovie.utils.MovieListActivity;
import com.example.myapplication.todaysmovie.R;
import com.example.myapplication.todaysmovie.pojo.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
                 private List<MovieModel> mMoviesList=new ArrayList<>();
                 private List<MovieModel>PopMoviesList = new ArrayList<>();
                  int itemViewType;
                  private static  final int  Display_Pop = 1 ;
                 private static final int Display_Search =2;
                 private OnMovieListener onMovieListener;

    public MoviesListAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view;
        if (viewType==Display_Search)
        {  view = layoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);

            return new MoviesViewHolder(view,onMovieListener) ;
        }
        else {
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item_popular,parent,false);

            return new PopMoviesViewHolder(view,onMovieListener) ;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    itemViewType = getItemViewType(position);
  if (itemViewType == Display_Search){
      ((MoviesViewHolder)holder).ratingBar.setRating(mMoviesList.get(position).getVote_average()/2);
      Glide.with( ((MoviesViewHolder)holder).itemView.getContext()).load("https://image.tmdb.org/t/p/w500"+mMoviesList.get(position).getPoster_path()).into(((MoviesViewHolder) holder).movie_image);
  }
  else {
      ((PopMoviesViewHolder)holder).ratingBar.setRating(PopMoviesList.get(position).getVote_average()/2);
      ((PopMoviesViewHolder)holder).release.setText("release date :"+PopMoviesList.get(position).getRelease_date());
      ((PopMoviesViewHolder)holder).language.setText("Language :"+PopMoviesList.get(position).getOriginal_language());
      Glide.with(((PopMoviesViewHolder)holder).itemView.getContext()).load("https://image.tmdb.org/t/p/w500"+PopMoviesList.get(position).getPoster_path()).into(((PopMoviesViewHolder) holder).movie_image);
  }


    }

    @Override
    public int getItemCount() {
        if (itemViewType == Display_Pop)
        {
            return PopMoviesList.size();
        }
        return mMoviesList.size();

    }


    public void setmMoviesList(List<MovieModel> mMoviesList) {
        this.mMoviesList = mMoviesList;
        notifyDataSetChanged();
    }
    public void setmPopMoviesList(List<MovieModel> PopMoviesList) {
        this.PopMoviesList = PopMoviesList;
        notifyDataSetChanged();
    }




    public  MovieModel getSelectedMovie(int position){
        if (itemViewType ==Display_Search)
        {
            if (mMoviesList!=null)
            {
                if (mMoviesList.size() > 0)
                {
                    return mMoviesList.get(position);
                }
            }
        }
        if (itemViewType==Display_Pop)
        {
            if (PopMoviesList!=null)
            {
                if (PopMoviesList.size() > 0)
                {
                    return PopMoviesList.get(position);
                }
        }

        }



        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (MovieListActivity.isPopular){
            return Display_Pop;
        }
        else
            return Display_Search;

    }


}
