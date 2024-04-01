package com.example.academia_android_projeto_final;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import com.example.academia_android_projeto_final.adapters.PokemonAdapter;
import com.example.academia_android_projeto_final.models.Pokemon;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PokemonAdapterTest {

    private PokemonAdapter adapter;
    private Context mockContext;
    private ArrayList<Pokemon> pokemons;

    @Before
    public void setUp() {
        pokemons = new ArrayList<>();
        pokemons.add(new Pokemon("Bulbasaur", 1));
        pokemons.add(new Pokemon("Charmander", 4));
        adapter = new PokemonAdapter(mockContext, pokemons);
    }

    @Test
    public void testAdapterCorrectData() {
        ArrayList<Pokemon> adapterList = adapter.getPokemons();
        assertEquals(adapterList.get(0),pokemons.get(0));
        assertEquals(adapterList.get(1),pokemons.get(1));
    }

    @Test
    public void testNumberOfElements() {
        assertEquals(adapter.getItemCount(),pokemons.size());
    }


    @Test
    public void adapterNotNull() {
        assertNotNull(adapter);
    }


}
