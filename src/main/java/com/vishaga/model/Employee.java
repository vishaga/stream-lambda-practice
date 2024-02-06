package com.vishaga.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public record Employee(
        Long id, String firstName, String lastName, List<String> emails, String contactNumber,
        int age, LocalDate dob, Integer salary, Address address, Company company, Sex sex,
        MaritalStatus maritalStatus, String race, Position position, LocalDate dateOfJoining, List<String> skills) {

    public static Employee from(String line){
        String[] details = line.split("\t");
        Long id = Long.parseLong(details[0].trim());
        String firstName = details[1].trim();
        String lastName = details[2].trim();
        List<String> emails = Stream.of(details[3].split(",")).map(String::trim).toList();
        String contactNumber = details[4].trim();
        int age = Integer.parseInt(details[5].trim());
        LocalDate dob = LocalDate.parse(details[6]);
        Integer salary = Integer.parseInt(details[7].trim());
        Address address = Address.from(details[8].trim());
        Company company = Company.from(details[9].trim());
        Sex sex = Sex.valueOf(details[10].trim().toUpperCase());
        MaritalStatus maritalStatus = MaritalStatus.valueOf(details[11].replace(" ","_").trim().toUpperCase());
        String race = details[12].trim();
        Position position = Position.valueOf(details[13].trim().toUpperCase());
        LocalDate doj = LocalDate.parse(details[14]);
        List<String> skills = Stream.of(details[15].split(",")).map(String::trim).toList();
        return new Employee(id, firstName, lastName, emails, contactNumber, age, dob, salary, address, company, sex, maritalStatus, race,position,doj, skills);
    }

    public String name(){
        return this.firstName + " " + this.lastName;
    }

    public static Employee fake(){
        return new Employee(-1L, "Fake", "NA",
                List.of(),"NA", 0, LocalDate.now(),
                0,Address.fake(), Company.fake(), Sex.MALE,
                MaritalStatus.NEVER_MARRIED, "American",
                Position.DIRECTOR,LocalDate.now(), List.of());
    }

    @Override
    public String toString() {
        return  id + "\t" + firstName + "\t" + lastName + "\t" + String.join(",", emails) + "\t" +
                contactNumber + "\t" + age + "\t" + dob + "\t" + salary + "\t" + address + "\t" +
                company + "\t" + sex + "\t" + maritalStatus + "\t" + race + "\t" + position + "\t" +
                dateOfJoining + "\t" + String.join(",", skills);
    }
}

