package com.example.academia_android_projeto_final;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutPokemonResponse {

    @SerializedName("height")
    public String height;

    @SerializedName("weight")
    public String weight;

    @SerializedName("moves")
    public List<Move> moves;

    @SerializedName("types")
    public List<Move> types;



    public class MoveDetails {
        @SerializedName("name")
        public String name;
    }

    public class Move {
        @SerializedName("move")
        public MoveDetails moveDetails;
    }
}


