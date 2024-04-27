package com.ykotsiuba.clear_solution_test.utils;

public class StringUtils {

    public static boolean nullOrBlank(String s) {
        if(s != null) {
            return s.isBlank();
        } else {
            return true;
        }
    }
}
