package com.example.academia_android_projeto_final;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Pokemon> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemons = new ArrayList<Pokemon>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new PokemonAdapter(this,pokemons);

        pokemons.add(new Pokemon("Balbassaur","grass","poison",110,130));
        pokemons.add(new Pokemon("ivisaur","grass","poison",110,130));
        pokemons.add(new Pokemon("Balbassaur","grass","poison",110,130));


        recyclerView.setAdapter(myAdapter);

    }
}
