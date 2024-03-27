package com.example.academia_android_projeto_final;

import androidx.room.Entity;

import java.util.ArrayList;


public class Pokemon {

    private String name;
    private int id;
    private String type1;
    private String type2;
    private int weight;
    private int height;
    private ArrayList<String> moves;

    public Pokemon(String name,int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<String> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
