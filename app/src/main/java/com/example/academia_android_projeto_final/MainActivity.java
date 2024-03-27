package com.example.academia_android_projeto_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    Button btnFavourites;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.Adapter favouriteAdapter;
    RecyclerView.LayoutManager layoutManager;
    RetrofitAPICall apiInterface;
    ArrayList<Pokemon> pokemons;
    ArrayList<Pokemon> favourites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);

        pokemons = new ArrayList<>();
        favourites = new ArrayList<>();

        btnFavourites = findViewById(R.id.btnFav);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new PokemonAdapter(this,pokemons);
        recyclerView.setAdapter(myAdapter);

        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);

        Call<PokemonListResponse> call = apiInterface.getPokemon();


        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {

                PokemonListResponse data = response.body();

                if (data != null)
                {
                    for (int i = 0 ; i < data.resultDataList.size(); i++)
                    {
                        String name = data.resultDataList.get(i).name;
                        String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
                        int id = i+1;
                        pokemons.add(new Pokemon(capitalizedName,id));
                        myAdapter.notifyItemInserted(i);
                    }

                }
                else
                {
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido");
                call.cancel();
            }
        });



        btnFavourites.setOnClickListener(v -> {

            for (Map.Entry<String, ?> key:preferences.getAll().entrySet()) {
                favourites.add(new Pokemon(key.getKey(), (int) key.getValue()));
            }
            favouriteAdapter = new PokemonAdapter(v.getContext(),favourites);
            recyclerView.setAdapter(favouriteAdapter);

        });


    }
}
