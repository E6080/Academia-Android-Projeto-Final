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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPokemonActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    ImageView pokemonImage, favoriteIcon , ivType1, ivType2;
    TextView tvWeight, tvHeight, tvPokeName;
    ListView listView;

    RetrofitAPICall apiInterface;

    HashMap<String, Integer> typeToIcon = new HashMap<>();
    List<String> moves = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pokemon);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        pokemonImage = findViewById(R.id.ivPokemonImage);
        favoriteIcon = findViewById(R.id.ivFav);
        ivType2 = findViewById(R.id.ivType2);
        ivType1 = findViewById(R.id.ivType1);
        listView = findViewById(R.id.list);
        tvHeight = findViewById(R.id.height);
        tvWeight = findViewById(R.id.weight);
        tvPokeName = findViewById(R.id.pokemonName);

        typeToIcon.put("normal",R.drawable.normal_type_icon);
        typeToIcon.put("grass",R.drawable.grass_type_icon);
        typeToIcon.put("poison",R.drawable.poison_type_icon);
        typeToIcon.put("fire",R.drawable.fire_type_icon);
        typeToIcon.put("water",R.drawable.water_type_icon);
        typeToIcon.put("electric",R.drawable.electric_type_icon);
        typeToIcon.put("ice",R.drawable.ice_type_icon);
        typeToIcon.put("fighting",R.drawable.fighting_type_icon);
        typeToIcon.put("ground",R.drawable.ground_type_icon);
        typeToIcon.put("flying",R.drawable.flying_type_icon);
        typeToIcon.put("psychic",R.drawable.psychic_type_icon);
        typeToIcon.put("bug",R.drawable.bug_type_icon);
        typeToIcon.put("rock",R.drawable.rock_type_icon);
        typeToIcon.put("ghost",R.drawable.ghost_type_icon);
        typeToIcon.put("dragon",R.drawable.dragon_type_icon);
        typeToIcon.put("fairy",R.drawable.fairy_type_icon);

        Intent intent = getIntent();

        int index = intent.getIntExtra("Index",0);
        String pokemonName = intent.getStringExtra("Name");

        if (preferences.contains(pokemonName)) {
            favoriteIcon.setImageResource(R.drawable.fav_star_filled_icon);
        }
        else {
            favoriteIcon.setImageResource(R.drawable.fav_star_icon);
        }

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preferences.contains(pokemonName)) {
                    favoriteIcon.setImageResource(R.drawable.fav_star_icon);
                    editor.remove(pokemonName);
                }
                else {
                    favoriteIcon.setImageResource(R.drawable.fav_star_filled_icon);
                    editor.putInt(pokemonName,index);
                }

                editor.apply();
            }
        });



        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);

        if (pokemonName == null) {
            throw new RuntimeException("pokemonName was null");
        }

        Call<AboutPokemonResponse> call = apiInterface.getDetails(pokemonName.toLowerCase());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moves);
        call.enqueue(new Callback<AboutPokemonResponse>() {
            @Override
            public void onResponse(Call<AboutPokemonResponse> call, Response<AboutPokemonResponse> response) {

                if (response.body() == null) {
                    Log.println(Log.ERROR,"error","Body null");
                    call.cancel();
                }

                Glide.with(pokemonImage.getContext())
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+response.body().id+".png")
                        .override(600,600)
                        .into(pokemonImage);

                for (int i = 0; i < response.body().moves.size() ; i++) {
                    moves.add(response.body().moves.get(i).moveDetails.name);
                }


                listView.setAdapter(adapter);
                tvPokeName.setText(pokemonName);
                tvHeight.setText(getString(R.string.height_format, response.body().height));
                tvWeight.setText(getString(R.string.weight_format, response.body().weight));

                if (!response.body().types.isEmpty()) {
                    String type = response.body().types.get(0).typeDetails.name;

                    if (typeToIcon.containsKey(type) && typeToIcon.get(type) != null) {
                        ivType1.setImageResource(typeToIcon.get(type));
                    }
                }

                if (response.body().types.size() > 1) {
                    String type = response.body().types.get(1).typeDetails.name;
                    ivType2.setVisibility(View.VISIBLE);

                    if (typeToIcon.containsKey(type) && typeToIcon.get(type) != null) {
                        ivType2.setImageResource(typeToIcon.get(type));
                    }
                }

            }

            @Override
            public void onFailure(Call<AboutPokemonResponse> call, Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido: " + t.getMessage());
                call.cancel();
            }
        });

    }

}