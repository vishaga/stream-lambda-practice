package com.vishaga.model;

public record City(int rank, String name, String country, int population, int metroPopulation) {

    public static City from(String line){
        String attributes[] = line.split("\t");
        int rank = Integer.parseInt(attributes[0].trim());
        String name = attributes[0].trim();
        String country = attributes[0].trim();
        int population = Integer.parseInt(attributes[0].trim());
        int metroPopulation = Integer.parseInt(attributes[0].trim());
        return new City(rank, name, country, population, metroPopulation);
    }
}
