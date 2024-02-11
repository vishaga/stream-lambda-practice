package com.vishaga.model;

import java.util.List;
import java.util.stream.Stream;

public record Book(int publishedYear, String name, List<String> authors, String language,
                   String authorRating, double rating, int ratingCount, String genre,
                   double grossSales, double publisherRevenue, double price, int rank, String publisher,
                   int unitSold) {

    public static Book from(String line){
        String[] attributes = line.split("\t");
        int publishedYear = Integer.parseInt(attributes[0].trim());
        String name = attributes[1].trim();
        List<String> authors = Stream.of(attributes[2].split(",")).map(String::trim).toList();
        String language = attributes[3].trim();
        String authorRating = attributes[4].trim();
        double rating = Double.parseDouble(attributes[5].trim());
        int ratingCount = Integer.parseInt(attributes[6].trim());
        String genre = attributes[7].trim();
        double grossSales = Double.parseDouble(attributes[8].trim());
        double publisherRevenue = Double.parseDouble(attributes[9].trim());
        double price = Double.parseDouble(attributes[10].trim());
        int rank = Integer.parseInt(attributes[11].trim());
        String publisher = attributes[12].trim();
        int unitSold = Integer.parseInt(attributes[13].trim());
        return new Book(publishedYear,name,authors,language,authorRating,rating,ratingCount,genre,grossSales,publisherRevenue,price,rank,publisher,unitSold);
    }

    public static Book fake(){
        return new Book(1000, "Fake", List.of(),"NA",
                "NA",0,0,"NA", 0,
                0, 0, 0, "NA", 0);
    }
}
