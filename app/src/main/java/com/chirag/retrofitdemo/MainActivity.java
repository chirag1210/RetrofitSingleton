package com.chirag.retrofitdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chirag.retrofitdemo.adapter.CountriesRecylerViewAdapter;
import com.chirag.retrofitdemo.model.Info;
import com.chirag.retrofitdemo.model.Result;
import com.chirag.retrofitdemo.service.GetCountryDataService;
import com.chirag.retrofitdemo.service.RetrofitInstance;
import com.chirag.retrofitdemo.utils.Constants;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CountriesRecylerViewAdapter.TopMoviesAdapterListener {

    private ArrayList<Result> results;
    private Result result;
    private CountriesRecylerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_activity_main);
        getCountries();
        // getCountryByCountryCode();
        initAdapter();
    }

    private void getCountries() {

        GetCountryDataService getCountryDataService = RetrofitInstance.getService();
        Call<Info> call = getCountryDataService.getCountryList();
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(@NonNull Call<Info> call, @NonNull Response<Info> response) {

                Info info = response.body();
                if (info == null) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR_CODE_RESPONSE_BODY_NULL" + Constants.StatusCodes.ERROR_CODE_RESPONSE_BODY_NULL,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (info != null && info.getRestResponse() != null) {
                    results = (ArrayList<Result>) info.getRestResponse().getResult();

                    for (Result result : results) {
                        Log.e("Result----->", result.getName());
                    }
                    adapter.refreshRecyclerView(results);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Info> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                if (throwable instanceof UnknownHostException) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR_CODE_UNKNOWN_HOST_EXCEPTION" + Constants.StatusCodes.ERROR_CODE_UNKNOWN_HOST_EXCEPTION,
                            Toast.LENGTH_LONG).show();
                } else if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR_CODE_SOCKET_TIMEOUT" + Constants.StatusCodes.ERROR_CODE_SOCKET_TIMEOUT,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "ERROR_CODE_FAILURE_UNKNOWN" + Constants.StatusCodes.ERROR_CODE_FAILURE_UNKNOWN,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getCountryByCountryCode() {

        GetCountryDataService getCountryDataService = RetrofitInstance.getService();
        Call<Info> call = getCountryDataService.getCountryList("IN");
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(@NonNull Call<Info> call, @NonNull Response<Info> response) {

                Info info = response.body();
                if (info != null && info.getRestResponse() != null) {
                    result = (Result) info.getRestResponse().getResult();
                    Log.e("Result----->", result.getName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Info> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initAdapter() {

        /*
         * 1. Init List
         * */
        results = new ArrayList<>();

        /*
         * 2. Init Adapter
         * */
        adapter = new CountriesRecylerViewAdapter(results, this);

        /*
         * Set Adapter to RecylerView
         * */
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "" + results.get(position).getName(), Toast.LENGTH_LONG).show();
    }
}
