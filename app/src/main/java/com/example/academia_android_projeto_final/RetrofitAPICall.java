package com.example.academia_android_projeto_final;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPICall {


    @GET("pokemon?offset=0&limit=151")
    Call<APIResponse> getPokemon();

}
