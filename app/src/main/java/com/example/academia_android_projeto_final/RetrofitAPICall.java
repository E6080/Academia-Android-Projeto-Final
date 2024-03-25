package com.example.academia_android_projeto_final;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPICall {


    @GET("pokemon?offset=0&limit=151")
    Call<PokemonListResponse> getPokemon();

}
