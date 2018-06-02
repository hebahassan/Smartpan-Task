package com.example.cdc.smartpan_task.activities.Interface;

import com.example.cdc.smartpan_task.activities.Data.Countries;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface GetFunctionsInterface {

    /**
    * API URL
    * */
    public static String COUNTRIES_URL = "https://restcountries.eu/rest/v1";

    /**
    * Retrofit get json object function
    * */
    @GET("/all")
    public void getAllCountries(Callback<List<Countries>> countriesCallback);
}
