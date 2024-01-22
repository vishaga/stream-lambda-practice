package com.vishaga.model;

public record Address(String buildingNumber, String streetName, String city, String country, String zipCode) {

    public static Address from(String address){
        String[] addr = address.trim().split(",");
        String number = addr[0].trim();
        String streetName = addr[1].trim();
        String city = addr[2].trim();
        String country = addr[3].trim();
        String zip = addr[4].trim();
        return new Address(number, streetName, city, country, zip);
    }

    public static Address fake(){
        return new Address("00#", "Unknown", "BLR", "India", "123456");
    }

}