package com.example.academia_android_projeto_final;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutPokemonResponse {

    @SerializedName("height")
    public int height;

    @SerializedName("weight")
    public int weight;

    @SerializedName("moves")
    public List<String> moves;

    @SerializedName("types")
    public List<String> types;




}
