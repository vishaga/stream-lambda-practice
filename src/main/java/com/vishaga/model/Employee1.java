package com.vishaga.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.*;
import com.github.javafaker.Address;
import com.github.javafaker.Company;

import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties("imageUrl")
public class Employee1 {

    static Faker faker = new Faker();
    Address address1 = new Faker().address();

    Random rand = new Random();

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> emails = new ArrayList<>();

    private String contactNumber;

    private int age;

    private String dob;

    private int salary;

    private String address;

    public Employee1() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
        int i = rand.nextInt(95,100);
        while (i>=95){
            String email1 = email.replace("@dummyapis.com", i+"@randommail.com");
            emails.add(email1);
            i--;
        }
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSalary(int salary) {
        this.salary = 10500 * rand.nextInt(3,17);
    }

    public void setAddress(String address) {
        String number = address1.buildingNumber();
        String streetName = address1.streetName();
        String city = address1.city();
        String country = address1.country();
        String zip = address1.zipCode();
        this.address = String.format("%s,%s,%s,%s,%s",
                number, streetName, city, country,zip);
    }
    private String getMails(){
        return emails.stream().collect(Collectors.joining(","));
    }

    private String company(){
        Company company = faker.company();
        return company.name() + "," + company.industry() + "," + company.profession() + "," + company.logo();
    }

    @Override
    public String toString() {
        Demographic demographic = faker.demographic();
        String str = demographic.sex() + "\t"
         + demographic.maritalStatus()+ "\t"
         + demographic.race();
        return
                id + "\t"
                + firstName + "\t"
                + lastName + "\t"
                + emails.stream().collect(Collectors.joining(",")) + "\t"
                + contactNumber + "\t"
                + age + "\t"
                + dob + "\t"
                + salary + "\t"
                + address + "\t"
                + company() + "\t"
                + str + "\t" + role();
    }

    private Position role(){
        int index = rand.nextInt(1, 11);
        return Position.values()[index];
    }
}
