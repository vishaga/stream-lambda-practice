package com.vishaga.model;

public record Country(
        String name, String alphaCode1, String alphaCode2, Integer countryCode, Region region,
        String subRegion, IntermediateRegion intermediateRegion, Integer regionCode,
        Integer subRegionCode, Integer intermediateRegionCode) implements Comparable<Country>{

    public static Country from(String line){
        String[] attributes = line.split("~");
        String name = attributes[0].trim();
        String alphaCode1 = attributes[1].trim();
        String alphaCode2 = attributes[2].trim();
        Integer countryCode = Integer.parseInt(attributes[3].trim());
        Region region = Region.from(attributes[4]);
        String subRegion = attributes[5].trim();
        IntermediateRegion intermediateRegion = IntermediateRegion.from(attributes[6]);
        Integer regionCode = !attributes[7].trim().isBlank() ? Integer.parseInt(attributes[7].trim()) : -100;
        Integer subRegionCode = !attributes[8].trim().isBlank() ? Integer.parseInt(attributes[8].trim()) : -100;
        int intermediateRegionCode = -100;
        if(attributes.length>9)
            intermediateRegionCode = !attributes[9].trim().isBlank() ? Integer.parseInt(attributes[9].trim()) : -100;
        return new Country(name,alphaCode1,alphaCode2,countryCode,region,subRegion,intermediateRegion,regionCode,subRegionCode,intermediateRegionCode);
    }

    @Override
    public int compareTo(Country other) {
        return this.name.compareTo(other.name);
    }
}
