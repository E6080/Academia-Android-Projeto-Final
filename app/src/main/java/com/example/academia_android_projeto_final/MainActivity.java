package com.example.academia_android_projeto_final;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.academia_android_projeto_final.models.Pokemon;
import com.example.academia_android_projeto_final.retrofit.APIClient;
import com.example.academia_android_projeto_final.retrofit.RetrofitAPICall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    ImageView btnFavourites, home;
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

        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);

        PokemonListFragment pokemonListFragment = new PokemonListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.pokemon_list_fragment,pokemonListFragment);
        fragmentTransaction.commit();


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
            pokemonListFragment.updateRecyclerView(favourites);
        });

        home.setOnClickListener(v ->
        {
            pokemonListFragment.fullList();
        });

    }

}
