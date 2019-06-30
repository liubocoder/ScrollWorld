package com.lb.scroll;

import android.graphics.Color;

import java.util.Random;

public class ColorUtils {

    private static Random sRandom = new Random();
    private static byte[] sData = {0,0,0};

    public static int getRandomColor() {
        sRandom.nextBytes(sData);
        return Color.argb(0xFF, sData[0] | 0xFF, sData[1] | 0xFF, sData[2] | 0xFF);
    }

}
