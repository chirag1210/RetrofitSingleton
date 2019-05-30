package com.chirag.retrofitdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.chirag.retrofitdemo.model.Info;
import com.chirag.retrofitdemo.model.Result;
import com.chirag.retrofitdemo.service.GetCountryDataService;
import com.chirag.retrofitdemo.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Result> results;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  getCountries();
        getCountryByCountryCode();
    }

    private void getCountries() {

        GetCountryDataService getCountryDataService = RetrofitInstance.getService();
        Call<Info> call = getCountryDataService.getCountryList();
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(@NonNull Call<Info> call, @NonNull Response<Info> response) {

                Info info = response.body();
                if (info != null && info.getRestResponse() != null) {
                    results = (ArrayList<Result>) info.getRestResponse().getResult();

                    for (Result result : results) {
                        Log.e("Result----->", result.getName());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Info> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
}
