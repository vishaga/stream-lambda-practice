package com.vishaga.model;

public record Company(String name, String industry, String profession, String logo) {

    public static Company from(String details){
        String[] detail = details.split(",");
        return new Company(detail[0].trim(),detail[1].trim(),detail[2].trim(),detail[3].trim());
    }

    public static Company fake(){
        return new Company("Fake", "NA", "NA", "NA");
    }

    @Override
    public String toString() {
        return name  + "," + industry  + "," + profession  + "," + logo;
    }
}
