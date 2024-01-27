package com.vishaga.partialapp;

import com.vishaga.java.util.Function;

public class Repeater {

    public static Function<String,String> repeat(int counter){
        return (content) -> {
            StringBuilder sb = new StringBuilder();
            int c = counter;
            while(c-- > 0){
                sb.append(content);
            }
            return sb.toString();
        };

    }

    public static void main(String[] args) {
        String apply = repeat(2).apply("Gaurav|");
        System.out.println("apply = " + apply);
    }
}
