package com.myst.biomebackport.core.helper;

public class IntHelper {
    public static int wrapAround(int value, int min, int max) {
        int range = max - min + 1;
        if (value < min) {
            value += range * ((min - value) / range + 1);
        }
        return min + (value - min) % range;
    }
}
