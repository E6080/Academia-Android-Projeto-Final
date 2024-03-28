package com.example.academia_android_projeto_final;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academia_android_projeto_final.retrofit.APIClient;
import com.example.academia_android_projeto_final.retrofit.PokemonListResponse;
import com.example.academia_android_projeto_final.retrofit.RetrofitAPICall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    ImageView btnFavourites, home;
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

        btnFavourites = findViewById(R.id.favFilter);
        home = findViewById(R.id.ivHome);

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
            public void onResponse(@NonNull Call<PokemonListResponse> call, @NonNull Response<PokemonListResponse> response) {

                PokemonListResponse data = response.body();

                if (data != null)
                {
                    for (int i = 0 ; i < data.resultDataList.size(); i++)
                    {
                        String name = data.resultDataList.get(i).name;
                        String capitalizedName = capitalizeFirstLetter(name);
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
            public void onFailure(@NonNull Call<PokemonListResponse> call, @NonNull Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido");
                call.cancel();
            }
        });



        btnFavourites.setOnClickListener(v -> {

            Set<String> existingFavorites = new HashSet<>();

            for (Pokemon pokemon : favourites)
            {
                existingFavorites.add(pokemon.getName());
            }

            for (Map.Entry<String, ?> key:preferences.getAll().entrySet())
            {
                if (!existingFavorites.contains(key.getKey()))
                {
                    favourites.add(new Pokemon(key.getKey(), (int) key.getValue()));
                }
            }

            favouriteAdapter = new PokemonAdapter(v.getContext(),favourites);
            recyclerView.setAdapter(favouriteAdapter);
        });

        home.setOnClickListener(v ->
        {
            RecyclerView.Adapter helper = new PokemonAdapter(v.getContext(),pokemons);
            recyclerView.setAdapter(helper);
        });


    }

    public String capitalizeFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
