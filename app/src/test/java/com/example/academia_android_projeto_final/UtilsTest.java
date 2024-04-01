package com.example.academia_android_projeto_final;

import static org.junit.Assert.assertEquals;

import com.example.academia_android_projeto_final.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;

public class UtilsTest
{

    @Test
    public void testCapitalizeFirstLetter() {
        assertEquals("Bulbassaur", Utils.capitalizeFirstLetter("bulbassaur"));
        assertEquals("Squirtle",Utils.capitalizeFirstLetter("squirtle"));
    }


}
