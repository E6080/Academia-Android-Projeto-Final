package com.example.academia_android_projeto_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.academia_android_projeto_final.adapters.PokemonAdapter;
import com.example.academia_android_projeto_final.models.Pokemon;
import com.example.academia_android_projeto_final.retrofit.APIClient;
import com.example.academia_android_projeto_final.retrofit.PokemonListResponse;
import com.example.academia_android_projeto_final.retrofit.RetrofitAPICall;
import com.example.academia_android_projeto_final.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonListFragment extends Fragment {
    private RetrofitAPICall apiInterface;
    private ArrayList<Pokemon> pokemons;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView.Adapter myAdapter;
    RecyclerView.Adapter helper;
    RecyclerView.Adapter favouriteAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemons = new ArrayList<>();
        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new PokemonAdapter(getContext(),pokemons);
        recyclerView.setAdapter(myAdapter);


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
                        String capitalizedName = Utils.capitalizeFirstLetter(name);
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


        return view;
    }


    public void updateRecyclerView (ArrayList<Pokemon> favourites)
    {
        favouriteAdapter = new PokemonAdapter(getContext(),favourites);
        recyclerView.swapAdapter(favouriteAdapter,true);

    }

    public void fullList ()
    {
        helper = new PokemonAdapter(getContext(),pokemons);
        recyclerView.swapAdapter(helper,true);
    }

}