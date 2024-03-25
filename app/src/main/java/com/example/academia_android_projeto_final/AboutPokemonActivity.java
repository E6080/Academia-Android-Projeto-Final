package com.example.academia_android_projeto_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPokemonActivity extends AppCompatActivity {

    ImageView pokemonImage, favoriteIcon;
    TextView tvWeight, tvHeight;
    ListView listView;

    RetrofitAPICall apiInterface;

    List<String> moves = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pokemon);

        pokemonImage = findViewById(R.id.ivPokemonImage);
        favoriteIcon = findViewById(R.id.ivFav);
        listView = findViewById(R.id.list);
        tvHeight = findViewById(R.id.height);
        tvWeight = findViewById(R.id.weight);

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


        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);
        Call<AboutPokemonResponse> call = apiInterface.getDetails(""+(index+1));
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,moves);
        call.enqueue(new Callback<AboutPokemonResponse>() {
            @Override
            public void onResponse(Call<AboutPokemonResponse> call, Response<AboutPokemonResponse> response) {

                Log.d("TAG", "ENTROU");

                if (response.body() == null) {
                    Log.println(Log.ERROR,"error","Body null");
                    call.cancel();
                }

                for (int i = 0; i < response.body().moves.size() ; i++) {
                    moves.add(response.body().moves.get(i).moveDetails.name);
                }

                listView.setAdapter(adapter);

                tvHeight.setText(getString(R.string.height_format, response.body().height));
                tvWeight.setText(getString(R.string.weight_format, response.body().weight));
            }

            @Override
            public void onFailure(Call<AboutPokemonResponse> call, Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido: " + t.getMessage());
                call.cancel();
            }
        });

    }

}