package com.girimani.hjss.utils;

public class Helper {
    public static String getRatingString(int rating) {
        return switch (rating) {
            case 1 -> "Very dissatisfied";
            case 2 -> "Dissatisfied";
            case 3 -> "Ok";
            case 4 -> "Satisfied";
            case 5 -> "Very Satisfied";
            default -> "Not rated";
        };
    }
}
