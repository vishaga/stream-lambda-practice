package com.vishaga.model;

public record USUniversity(String name, String city, String state, int rank, double fee, double stateFee,long enrolledStudent) {

    public static USUniversity from(String line){
        String[] attributes = line.trim().split("\t");
        String name = attributes[0].trim();
        String[] cityAndState = attributes[1].trim().split(",");
        String city = cityAndState[0].trim(), state = cityAndState[0].trim();
        if(cityAndState.length != 1){
            state = cityAndState[1].trim();
        }
        int rank = Integer.parseInt(attributes[2].trim());
        double fee = Double.parseDouble(attributes[3].trim());
        double stateFee = fee;
        if(!attributes[4].trim().equals("-")){
            stateFee = Double.parseDouble(attributes[4].trim());
        }
        long enrolledStudent = Long.parseLong(attributes[5].trim());
        return new USUniversity(name,city,state,rank,fee,stateFee,enrolledStudent);
    }
}
