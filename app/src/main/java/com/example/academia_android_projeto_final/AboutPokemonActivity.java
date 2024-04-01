package com.example.academia_android_projeto_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.academia_android_projeto_final.retrofit.APIClient;
import com.example.academia_android_projeto_final.retrofit.AboutPokemonResponse;
import com.example.academia_android_projeto_final.retrofit.RetrofitAPICall;
import com.example.academia_android_projeto_final.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPokemonActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    ImageView pokemonImage, favoriteIcon , ivType1, ivType2;
    TextView tvWeight, tvHeight, tvPokeName;
    ListView listView;

    RetrofitAPICall apiInterface;

    List<String> moves = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pokemon);

        pokemonImage = findViewById(R.id.ivPokemonImage);
        favoriteIcon = findViewById(R.id.ivFav);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        PokemonInfoFragment pokemonInfoFragment = new PokemonInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.pokemon_info_fragment,pokemonInfoFragment);
        fragmentTransaction.commit();


        Intent intent = getIntent();

        int index = intent.getIntExtra("Index",0);
        pokemonName = intent.getStringExtra("Name");

        if (preferences.contains(pokemonName)) {
            favoriteIcon.setImageResource(R.drawable.fav_star_filled_icon);
        }
        else {
            favoriteIcon.setImageResource(R.drawable.fav_star_icon);
        }

        favoriteIcon.setOnClickListener(v -> {

            if (preferences.contains(pokemonName)) {
                favoriteIcon.setImageResource(R.drawable.fav_star_icon);
                editor.remove(pokemonName);
            }
            else {
                favoriteIcon.setImageResource(R.drawable.fav_star_filled_icon);
                editor.putInt(pokemonName,index);
            }

            editor.apply();
        });



        if (pokemonName == null) {
            throw new RuntimeException("pokemonName was null");
        }

        Glide.with(pokemonImage.getContext())
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+index+".png")
                .override(600,600)
                .into(pokemonImage);


    }

    public String getPokemonName() {
        return pokemonName;
    }
}