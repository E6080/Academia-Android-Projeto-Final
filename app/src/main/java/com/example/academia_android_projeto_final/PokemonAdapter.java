package com.example.academia_android_projeto_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemons;

    public PokemonAdapter(Context context, ArrayList<Pokemon> list)
    {
        pokemons = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvPokemonName;
        ImageView imagePokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePokemon = itemView.findViewById(R.id.ivPokemonImage);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
        }
    }
    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pokemon, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(pokemons.get(position));
        holder.imagePokemon.setImageResource(R.drawable.ic_launcher_foreground);
        holder.tvPokemonName.setText(pokemons.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return pokemons.size();
    }
}
