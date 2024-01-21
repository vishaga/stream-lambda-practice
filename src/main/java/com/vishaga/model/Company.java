package com.vishaga.model;

public record Company(String name, String industry, String profession, String logo) {

    public static Company from(String details){
        String[] detail = details.split(",");
        return new Company(detail[0].trim(),detail[1].trim(),detail[2].trim(),detail[3].trim());
    }
}
