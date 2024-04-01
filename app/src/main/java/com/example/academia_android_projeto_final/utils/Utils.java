package com.example.academia_android_projeto_final.utils;

import com.example.academia_android_projeto_final.R;

import java.util.HashMap;

public class Utils {


    public static String capitalizeFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static HashMap<String, Integer> setUpTypeToIcon(HashMap<String, Integer> typeToIcon)
    {
        typeToIcon.put("normal", R.drawable.normal_type_icon);
        typeToIcon.put("grass",R.drawable.grass_type_icon);
        typeToIcon.put("poison",R.drawable.poison_type_icon);
        typeToIcon.put("fire",R.drawable.fire_type_icon);
        typeToIcon.put("water",R.drawable.water_type_icon);
        typeToIcon.put("electric",R.drawable.electric_type_icon);
        typeToIcon.put("ice",R.drawable.ice_type_icon);
        typeToIcon.put("fighting",R.drawable.fighting_type_icon);
        typeToIcon.put("ground",R.drawable.ground_type_icon);
        typeToIcon.put("flying",R.drawable.flying_type_icon);
        typeToIcon.put("psychic",R.drawable.psychic_type_icon);
        typeToIcon.put("bug",R.drawable.bug_type_icon);
        typeToIcon.put("rock",R.drawable.rock_type_icon);
        typeToIcon.put("ghost",R.drawable.ghost_type_icon);
        typeToIcon.put("dragon",R.drawable.dragon_type_icon);
        typeToIcon.put("fairy",R.drawable.fairy_type_icon);

        return typeToIcon;
    }

}
