package com.vishaga.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Movie2(String name, Integer releaseYear, LocalDate releaseDate, Set<String> genre,
                     int duration, List<String> releasedInCountries, Set<String> languages, List<String> directors, List<String> writers,
                     List<String> productionCompanies, Set<String> actors, double avgVote, Integer votes, long budget) {

    public static Movie2 from(String line){
        String[] strings = line.trim().split("\t");
        String name = strings[0].trim();
        Integer releaseYear = Integer.parseInt(strings[1].trim());
        LocalDate releaseDate = LocalDate.parse(strings[2].trim());
        Set<String> genre = Stream.of(strings[3].split(",")).map(String::trim).collect(Collectors.toSet());
        int duration = Integer.parseInt(strings[4].trim());
        List<String> releasedInCountry = Stream.of(strings[5].split(",")).map(String::trim).toList();
        Set<String> languages = Stream.of(strings[6].split(",")).map(String::trim).collect(Collectors.toSet());
        List<String> directors = Stream.of(strings[7].split(",")).map(String::trim).toList();
        List<String> writers = Stream.of(strings[8].split(",")).map(String::trim).toList();
        List<String> productionCompanies = Stream.of(strings[9].split(",")).map(String::trim).toList();
        Set<String> actors = Stream.of(strings[10].split(",")).map(String::trim).collect(Collectors.toSet());
        double avgVote = Double.parseDouble(strings[11].trim());
        Integer votes = Integer.parseInt(strings[12].trim());
        long budgetInDollar = Long.parseLong(strings[13].trim().substring(1));
        return new Movie2(name,releaseYear,releaseDate, genre,duration,releasedInCountry,languages,directors,writers,productionCompanies,actors,avgVote,votes,budgetInDollar);
    }

    public static Movie2 fake(){
        return new Movie2("NA",0, LocalDate.now(), Set.of(),0, List.of(), Set.of(),List.of(),List.of(),List.of(),Set.of(),0d,0,0);
    }
}
