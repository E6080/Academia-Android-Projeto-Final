package com.example.academia_android_projeto_final;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.test.core.app.ApplicationProvider;

import com.example.academia_android_projeto_final.adapters.PokemonAdapter;
import com.example.academia_android_projeto_final.models.Pokemon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
@RunWith(RobolectricTestRunner.class)
@Config (sdk = Build.VERSION_CODES.P)
public class PokemonAdapterTest {

    private PokemonAdapter adapter;
    @Mock
    private Context mockContext;
    private ViewGroup parent;
    private ArrayList<Pokemon> pokemons;

    @Before
    public void setUp() {
        mockContext = ApplicationProvider.getApplicationContext();
//        parent = new FrameLayout(mockContext);
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

//    @Test
//    public void testViewHolder()
//    {
//
//        View view = LayoutInflater.from(mockContext).inflate(R.layout.list_pokemon, null, false);
//        PokemonAdapter.ViewHolder viewHolder = new PokemonAdapter.ViewHolder(view);
//
//
//        verify(viewHolder.tvPokemonName).setText("Bulbasaur");
//        verify(viewHolder.imagePokemon).setImageResource(anyInt());
//    }

    @Test
    public void testNumberOfElements() {
        assertEquals(adapter.getItemCount(),pokemons.size());
    }


    @Test
    public void adapterNotNull() {
        assertNotNull(adapter);
    }


}
