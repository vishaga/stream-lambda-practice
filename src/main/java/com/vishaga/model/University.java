package com.vishaga.model;

public record University(String name, String country, int establishedYear, int numOfFaculty,
                         int numOfStaff, int	numOfDoctoral, int numOfPostGrad, int numOfUnderGrad,
                         int	numOfStudent) {

    public static University from(String line){
        String[] strings = line.split("\t");
        String name = strings[0].trim();
        String country = strings[1].trim();
        int establishedYear = Integer.parseInt(strings[2].trim());
        int numOfFaculty = Integer.parseInt(strings[3].trim());
        int numOfStaff = Integer.parseInt(strings[4].trim());
        int numOfDoctoral = Integer.parseInt(strings[5].trim());
        int numOfPostGrad = Integer.parseInt(strings[6].trim());
        int numOfUnderGrad = Integer.parseInt(strings[7].trim());
        int numOfStudent = Integer.parseInt(strings[8].trim());
        return new University(name,country,establishedYear,numOfFaculty,numOfStaff,numOfDoctoral,numOfPostGrad,numOfUnderGrad,numOfStudent);
    }
}
