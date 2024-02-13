package com.vishaga.model;

public record Car(Integer id, String brand, String model, String color, Integer year, Double price) {

    public static Car from(String line){
        String[] attributes = line.split("\t");
        int id = Integer.parseInt(attributes[0].trim());
        String brand = attributes[1].trim();
        String model = attributes[2].trim();
        String color = attributes[3].trim();
        int year = Integer.parseInt(attributes[4].trim());
        double price = Double.parseDouble(attributes[5].trim());
        return new Car(id, brand, model, color, year, price);
    }

    public static Car fake(){
        return new Car(0, "NA","NA","NA", 0, 0d);
    }
}
