package com.vishaga.model;

import java.util.List;

public record Population (
        String countryCode, String country, String cityCode, String city,
        Integer in_1950,Integer in_1960,Integer in_1970,Integer in_1980,
        Integer in_1990,Integer in_2000,Integer in_2010,Integer in_2020, Integer in_2030){
    
    static final int MULTIPLE = 1000;

    static final List<String> COUNTRY_IDS = List.of("949");

    public static Population from(String line){
        String[] strings = line.split("\t");
        String countryCode = strings[0].trim();
        String country = strings[1].trim();
        String cityCode = strings[2].trim();
        String city = strings[3].trim();
        Integer in_1950 = Integer.parseInt(strings[4].trim()) * factor(countryCode);
        Integer in_1960 = Integer.parseInt(strings[5].trim()) * factor(countryCode);
        Integer in_1970 = Integer.parseInt(strings[6].trim()) * factor(countryCode);
        Integer in_1980 = Integer.parseInt(strings[7].trim()) * factor(countryCode);
        Integer in_1990 = Integer.parseInt(strings[8].trim()) * factor(countryCode);
        Integer in_2000 = Integer.parseInt(strings[9].trim()) * factor(countryCode);
        Integer in_2010 = Integer.parseInt(strings[10].trim()) * factor(countryCode);
        Integer in_2020 = Integer.parseInt(strings[11].trim()) * factor(countryCode);
        Integer in_2030 = Integer.parseInt(strings[12].trim()) * factor(countryCode);
        return new Population(countryCode,country,cityCode,city,
                in_1950,in_1960,in_1970,in_1980,in_1990,in_2000,in_2010,in_2020,in_2030);
    }

    public static Population fake(){
        return new Population("NA", "UNKNOWN", "NA", "UNKNOWN",
        -1, -1, -1, -1, -1, -1, -1, -1, -1);
    }

    private static int factor(String countryCode){
        return COUNTRY_IDS.contains(countryCode) ? 1: MULTIPLE;
    }
}
