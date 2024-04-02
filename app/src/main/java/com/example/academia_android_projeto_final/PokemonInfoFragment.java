package com.example.academia_android_projeto_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.academia_android_projeto_final.retrofit.APIClient;
import com.example.academia_android_projeto_final.retrofit.AboutPokemonResponse;
import com.example.academia_android_projeto_final.retrofit.RetrofitAPICall;
import com.example.academia_android_projeto_final.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonInfoFragment extends Fragment {


    ImageView ivType1, ivType2;
    TextView tvWeight, tvHeight, tvPokeName;
    ListView listView;
    HashMap<String, Integer> helper;

    HashMap<String, Integer> typeToIcon;
    RetrofitAPICall apiInterface;
    List<String> moves = new ArrayList<>();
    ArrayAdapter<String> adapter;
    AboutPokemonActivity aboutPokemonActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pokemon_info, container, false);


        ivType2 = view.findViewById(R.id.ivType2);
        ivType1 = view.findViewById(R.id.ivType1);
        listView = view.findViewById(R.id.list);
        tvHeight = view.findViewById(R.id.height);
        tvWeight = view.findViewById(R.id.weight);
        tvPokeName = view.findViewById(R.id.pokemonName);


        aboutPokemonActivity = (AboutPokemonActivity) getActivity();
        apiInterface = APIClient.getClient().create(RetrofitAPICall.class);
        listView = view.findViewById(R.id.list);
        moves = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, moves);

        helper = new HashMap<>();

        typeToIcon = Utils.setUpTypeToIcon(helper);

        Call<AboutPokemonResponse> call = apiInterface.getDetails(aboutPokemonActivity.pokemonName.toLowerCase());


        call.enqueue(new Callback<AboutPokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<AboutPokemonResponse> call, @NonNull Response<AboutPokemonResponse> response) {

                if (response.body() == null) {
                    Log.println(Log.ERROR,"error","Body null");
                    call.cancel();
                }

                for (int i = 0; i < response.body().moves.size() ; i++) {
                    String capitalized = Utils.capitalizeFirstLetter(response.body().moves.get(i).moveDetails.name);
                    moves.add(capitalized);
                }

                listView.setAdapter(adapter);

                tvPokeName.setText(aboutPokemonActivity.pokemonName);
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


        return view;
    }
}