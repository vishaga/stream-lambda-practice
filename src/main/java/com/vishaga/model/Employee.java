package com.vishaga.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public record Employee(
        Long id, String firstName, String lastName, List<String> emails, String contactNumber,
        int age, LocalDate dob, Integer salary, Address address, Company company, Sex sex,
        MaritalStatus maritalStatus, String race) {

    public static Employee from(String line){
        String details[] = line.split("\t");
        Long id = Long.parseLong(details[0].trim());
        String firstName = details[1].trim();
        String lastName = details[2].trim();
        List<String> emails = Stream.of(details[3].split(",")).map(String::trim).toList();
        String contactNumber = details[4].trim();
        int age = Integer.parseInt(details[5].trim());
        List<Integer> dates = Stream.of(details[6].trim().split("/")).mapToInt(Integer::parseInt).boxed().toList();
        LocalDate dob = LocalDate.of(dates.get(2), dates.get(1), dates.get(0));
        Integer salary = Integer.parseInt(details[7].trim());
        Address address = Address.from(details[8].trim());
        Company company = Company.from(details[9].trim());
        Sex sex = Sex.valueOf(details[10].trim().toUpperCase());
        MaritalStatus maritalStatus = MaritalStatus.valueOf(details[11].replace(" ","_").trim().toUpperCase());
        String race = details[12].trim();
        return new Employee(id, firstName, lastName, emails, contactNumber, age, dob, salary, address, company, sex, maritalStatus, race);
    }


 enum Sex{
     MALE, FEMALE;
 }

 enum MaritalStatus{
     MARRIED("Married"),
     UNMARRIED("Married"),
     SEPARATED("Separated"),
     DIVORCED("Divorced"),
     WIDOWED("Widowed"),
     NEVER_MARRIED("Never married");

     final String status;

     MaritalStatus(String status){
         this.status = status;
     }
 }
}

