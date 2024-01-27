package com.vishaga.model;

import java.util.List;

public record Article(int inceptionYear, String title, String articleType, List<String> authors) {

    public static Article from(String line){
        String[] attributes = line.split("\t");
        int inceptionYear = Integer.parseInt(attributes[0].trim());
        String title = attributes[1].trim();
        String articleType = attributes[2].trim();
        List<String> authors = List.of();
        if(attributes.length > 3){
            authors = List.of(attributes[3].trim().split(","));
        }
        return new Article(inceptionYear, title, articleType, authors);
    }
}
