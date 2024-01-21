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

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String email;

    private List<String> emails = new ArrayList<>();

    private String contactNumber;

    private int age;

    private String dob;

    private int salary;

    private String address;

    public Employee1() {
    }

    public Employee1(Integer id, String firstName, String lastName, String email, String contactNumber, int age, String dob, int salary, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.age = age;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
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
        int i = rand.nextInt(96,98);
        //System.out.println("Email: " + email + ", " + i);
        while (i>=96){
            String email1 = email.replace("@dummyapis.com", i+"@randommail.com");
            //System.out.println("email1 = " + email1);
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

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return
                email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public int getSalary() {
        return salary;
    }

    public String getAddress() {
        return address;
    }

    private String getMails(){
        return emails.stream().collect(Collectors.joining(","));
    }

    private String company(){
        //Friends friends = faker.friends();
        //System.out.println("======");
        //System.out.println(friends.character());
        //System.out.println(friends.quote());
        //System.out.println(friends.location());
        Company company = faker.company();
        return company.name() + "," + company.industry() + "," + company.profession() + "," + company.logo();
    }

    @Override
    public String toString() {
        Demographic demographic = faker.demographic();
        String str = demographic.sex() + "\t"
         //+ demographic.demonym() + ","
         //+ demographic.educationalAttainment() + ","
         + demographic.maritalStatus()+ "\t"
         + demographic.race();
        //System.out.println(faker.job().field());
        //System.out.println(faker.job().keySkills());
        //System.out.println(faker.job().position());
        //System.out.println(faker.job().seniority());
        //System.out.println(faker.educator().university());
//        Set<String> set = new HashSet<>();
//        for(int i=0;i<10000;i++){
//            set.add(faker.educator().course());
//        }
//        System.out.println(set);
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
                + str;
    }
}
