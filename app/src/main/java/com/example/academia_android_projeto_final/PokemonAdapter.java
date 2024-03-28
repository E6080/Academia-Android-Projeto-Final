package com.example.academia_android_projeto_final;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private static ArrayList<Pokemon> pokemons;

    public PokemonAdapter(Context context, ArrayList<Pokemon> list)
    {
        pokemons = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvPokemonName;
        ImageView imagePokemon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePokemon = itemView.findViewById(R.id.ivPokemonImage);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);

            imagePokemon.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), AboutPokemonActivity.class);
                intent.putExtra("Index",pokemons.get(getAdapterPosition()).getId());
                intent.putExtra("Name",pokemons.get(getAdapterPosition()).getName());
                v.getContext().startActivity(intent);
            });
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
        Glide.with(holder.itemView)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemons.get(position).getId()+".png")
                .override(400,360)
                .into(holder.imagePokemon);
        holder.tvPokemonName.setText(pokemons.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }
}
