package com.vishaga.model;

public record NobelInfo(String name, int year, String domain, String sex,
                        String countryOfBirth, int yearOfBirth,
                        int age, String country, String university) {

    public static NobelInfo from(String line){
        String[] detail = line.split("\t");
        String name = detail[0].trim();
        String yearDomainInfo = detail[1].trim();
        int year = Integer.parseInt(yearDomainInfo.substring(0,4));
        String domain = yearDomainInfo.substring(8);
        String sex = detail[2].trim();
        String countryOfOrigin = detail[3].trim();
        int yearOfBirth = Integer.parseInt(detail[4].trim());
        int age = Integer.parseInt(detail[5].trim());
        String country = detail[6].trim();
        String university = "NA";
        if(detail.length > 7)
            university = detail[7].trim();
        return new NobelInfo(name,year,domain,sex,countryOfOrigin,yearOfBirth,age,country,university);
    }
}
