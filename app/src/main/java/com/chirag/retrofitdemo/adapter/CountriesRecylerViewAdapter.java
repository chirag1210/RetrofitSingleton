package com.chirag.retrofitdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chirag.retrofitdemo.R;
import com.chirag.retrofitdemo.model.Result;

import java.util.ArrayList;

public class CountriesRecylerViewAdapter extends RecyclerView.Adapter<CountriesRecylerViewAdapter.CountriesViewHolder>
{ /*
 * Global Instance of Interface
 * */
    private TopMoviesAdapterListener listener;

    /*
     * Global Instance of List to store all the movies
     * */
    private ArrayList<Result> topMoviesModelList;

    /*
     * Constructor
     * */
    public CountriesRecylerViewAdapter(ArrayList<Result> topMoviesModelList, TopMoviesAdapterListener listener)
    {
        this.topMoviesModelList = topMoviesModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CountriesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position)
    {
        Result topMoviesModel = topMoviesModelList.get(position);

        /*
         * Update Movie Title
         * */
        holder.txtMovieTitle.setText(topMoviesModel.getName());

        /*
         * Click Listener on Item
         * */
        holder.itemView.setOnClickListener(v ->
        {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount()
    {
        return topMoviesModelList.size();
    }

    public void refreshRecyclerView(ArrayList<Result> newMoviesList)
    {
        if (this.topMoviesModelList != null)
        {
            this.topMoviesModelList.clear();
            this.topMoviesModelList.addAll(newMoviesList);
        }
        else
        {
            this.topMoviesModelList = newMoviesList;
        }

        notifyDataSetChanged();
    }

    /*
     * ViewHolder
     * */
    class CountriesViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtMovieTitle;

        /*
         * Constructor
         * */
        public CountriesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtMovieTitle=itemView.findViewById(android.R.id.text1);

        }
    }

    /*
     * Interface for Callbacks to MainActivity
     * */
    public interface TopMoviesAdapterListener
    {
        void onItemClick(int position);
    }
}