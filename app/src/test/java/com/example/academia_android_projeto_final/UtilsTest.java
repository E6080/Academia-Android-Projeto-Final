package com.example.academia_android_projeto_final;

import static org.junit.Assert.assertEquals;

import com.example.academia_android_projeto_final.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class UtilsTest
{

    @Test
    public void testCapitalizeFirstLetter() {
        assertEquals("Bulbassaur", Utils.capitalizeFirstLetter("bulbassaur"));
        assertEquals("Squirtle",Utils.capitalizeFirstLetter("squirtle"));
    }

    @Test
    public void testSetUpTypeToIcon() {
        HashMap<String, Integer> typeToIcon = new HashMap<>();
        HashMap<String, Integer> result = Utils.setUpTypeToIcon(typeToIcon);
        assertEquals(Integer.valueOf(R.drawable.normal_type_icon), result.get("normal"));
        assertEquals(Integer.valueOf(R.drawable.grass_type_icon), result.get("grass"));
        assertEquals(Integer.valueOf(R.drawable.fire_type_icon), result.get("fire"));
        assertEquals(Integer.valueOf(R.drawable.water_type_icon), result.get("water"));
    }


}
