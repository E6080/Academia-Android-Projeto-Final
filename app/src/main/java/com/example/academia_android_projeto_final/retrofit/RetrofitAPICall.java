package com.example.academia_android_projeto_final.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPICall {


    @GET("pokemon?offset=0&limit=151")
    Call<PokemonListResponse> getPokemon();

    @GET("pokemon/{id}")
    Call<AboutPokemonResponse> getDetails(@Path("id")String id);

}
