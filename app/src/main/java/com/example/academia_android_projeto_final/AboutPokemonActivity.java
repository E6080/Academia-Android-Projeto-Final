package com.example.academia_android_projeto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class AboutPokemonActivity extends AppCompatActivity {

    ImageView pokemonImage, favoriteIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pokemon);

        pokemonImage = findViewById(R.id.ivPokemonImage);
        favoriteIcon = findViewById(R.id.ivFav);

        Intent intent = getIntent();

        int index = intent.getIntExtra("Index",0);

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteIcon.setImageResource(R.drawable.fav_star_filled_icon);
            }
        });


        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+(index+1)+".png")
                .override(600,600)
                .into(pokemonImage);

    }
}