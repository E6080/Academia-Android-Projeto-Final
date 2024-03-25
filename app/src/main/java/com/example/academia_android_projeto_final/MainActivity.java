package com.example.academia_android_projeto_final;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    RetrofitAPICall apiInterface;
    ArrayList<Pokemon> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemons = new ArrayList<>();

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
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {

                PokemonListResponse data = response.body();

                if (data != null)
                {
                    for (int i = 0 ; i < data.resultDataList.size(); i++)
                    {
                        pokemons.add(new Pokemon(data.resultDataList.get(i).name));
                        myAdapter.notifyItemInserted(i);
                    }

                }
                else
                {
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido");
                call.cancel();
            }
        });




    }
}
