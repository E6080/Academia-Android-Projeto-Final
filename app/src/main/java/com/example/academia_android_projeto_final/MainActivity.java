package com.example.academia_android_projeto_final;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    RetrofitAPICall apiInterface;
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
        recyclerView.setAdapter(myAdapter);
//        pokemons.add(new Pokemon("Balbasaur","grass","poison",130,130));

        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);

        Call<APIResponse> call = apiInterface.getPokemon();

        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                Log.println(Log.DEBUG,"TAG","Entrou");

                APIResponse data = response.body();

                if (data != null)
                {
                    Log.println(Log.DEBUG,"TAG","Entrou");

                    for (int i = 0 ; i < data.resultDataList.size(); i++)
                    {
                        pokemons.add(new Pokemon(data.resultDataList.get(i).name,"grass","poison",130,130));
                        myAdapter.notifyItemInserted(i);
                    }

                }
                else
                {
                    Log.println(Log.ERROR,"TAG","Entrou");
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.println(Log.ERROR,"error","Falhou no pedido");
                call.cancel();
            }
        });




    }
}
