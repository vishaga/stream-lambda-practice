package com.vishaga.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Movie2(String name, Integer releaseYear, LocalDate releaseDate, Set<String> genre,
                     String duration, String country, String language, String director, String writer,
                     String productionCompany,  Set<String> actors, String avgVote, Integer votes, String budget) {

    public static Movie2 from(String line){
        String[] strings = line.trim().split("\t");
        String name = strings[0].trim();
        Integer releaseYear = Integer.parseInt(strings[1].trim());
        LocalDate releaseDate = LocalDate.of(1800,3,3);
        if(strings[2].trim().length() == 4){
            releaseDate = LocalDate.of(Integer.parseInt(strings[2].trim()),1,1);
        }else {
            releaseDate = LocalDate.parse(strings[2].trim());
        }
        Set<String> genre = Stream.of(strings[3].split(",")).collect(Collectors.toSet());
        String duration = strings[4].trim();
        String country = strings[5].trim();
        String language = strings[6].trim();
        String director = strings[7].trim();
        String writer = strings[8].trim();
        String productionCompany = strings[9].trim();
        Set<String> actors = Stream.of(strings[10].split(",")).collect(Collectors.toSet());
        String avgVote = strings[11].trim();
        Integer votes = Integer.parseInt(strings[12].trim());
        String budget = "";
        if(strings.length > 13)
         budget = strings[13].trim();
        return new Movie2(name,releaseYear,releaseDate, genre,duration,country,language,director,writer,productionCompany,actors,avgVote,votes,budget);
    }
}
