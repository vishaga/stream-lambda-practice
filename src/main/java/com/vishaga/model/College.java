package com.vishaga.model;

public record College(int id, String name,String address, String city, String state, int zipCode,
                      String telephone, String type, String status, int population, String county) {

    public static College from(String line){
        System.out.println("line = " + line);
        String[] attr = line.split("\t");
        int id = Integer.parseInt(attr[0].trim());
        String name = attr[1].trim();
        String address = attr[2].trim();
        String city = attr[3].trim();
        String state = attr[4].trim();
        int zipcode = "NOT AVAILABLE".equals(attr[5].trim()) ? 0 : Integer.parseInt(attr[5].trim());
        String telephone = attr[6].trim();
        String type = attr[7].trim();
        String status = attr[8].trim();
        int population = Integer.parseInt(attr[9].trim());
        String county = attr[10].trim();
        return new College(id, name, address, city, state, zipcode, telephone, type,status,population,county);
    }
}
