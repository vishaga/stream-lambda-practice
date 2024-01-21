package com.vishaga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public record Movie (
        String name,int releaseYear, String duration, List<String> genres, double rating,
        Long votes, String director, List<String> actors){

    public static Movie fake(){
        return new Movie("Fake", 1000, "0 Min", List.of(), 1.0, 0L, "NA", List.of());
    }

    public static Movie from(String line) {
        List<String> attributes = Pattern.compile("\t").splitAsStream(line).toList();
        String name = attributes.get(Field.NAME.index).trim();
        int releaseYear = Integer.parseInt(attributes.get(Field.YEAR.index).trim());
        String duration = attributes.get(Field.DURATION.index).trim();
        List<String> genres = Pattern.compile(",").splitAsStream(attributes.get(Field.GENRE.index)).map(String::trim).toList();
        double rating = Double.parseDouble(attributes.get(Field.RATING.index).trim());
        Long votes = Long.parseLong(attributes.get(Field.VOTES.index).trim().replace(",",""));
        String director = attributes.get(Field.DIRECTOR.index).trim();
        int index = Field.ACTORS.index;
        List<String> actors = new ArrayList<>();
        while (index < attributes.size() ){
            actors.add(attributes.get(index++).trim());
        }
        List<String> actorList = List.copyOf(actors);
        return new Movie(name,releaseYear,duration,genres,rating,votes,director,actorList);
    }

    private enum Field{
        NAME(0),
        YEAR(1),
        DURATION(2),
        GENRE(3),
        RATING(4),
        VOTES(5),
        DIRECTOR(6),
        ACTORS(7);

        final int index;
        Field(int index){
            this.index = index;
        }

    }
}
