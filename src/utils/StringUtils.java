package com.utils;

public class StringUtils {
    
    public static boolean isEmpty(String variable) {
        return (variable == null || variable.trim().isEmpty());
    }
    
    public static boolean isNotEmpty(String variable) {
        return !isEmpty(variable);
    }
    
    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
