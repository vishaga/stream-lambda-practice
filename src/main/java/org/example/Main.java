package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.vishaga.model.Employee;
import com.vishaga.model.Employee1;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception{
        pullEmployee();
    }

    private static void pullEmployee() throws Exception{
        HttpClient build = HttpClient.newBuilder().build();

        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create("https://hub.dummyapis.com/employee?noofRecords=1000&idStarts=29001"))
                .GET()
                .build();

        HttpResponse<String> str = build.send(r, HttpResponse.BodyHandlers.ofString());
        //System.out.println("str.body() = " + str.body());

        ObjectMapper mapper = new ObjectMapper();
        Employee1[] employee = mapper.readValue(str.body(), Employee1[].class);
        Employee emp = Employee.from(employee[0].toString());
        //System.out.println("emp = " + emp);
        write(employee);
    }

    private static void write(Employee1[] employees) throws Exception{
        String path = Reader.class.getResource("/_employee.txt").getPath();
        File fileName = new File(path);
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(Employee1 e: employees){
            printWriter.write(e.toString() +"\n");
        }
        printWriter.close();
    }

    private static void author(int numberOfBooks) throws Exception{
        Faker faker = new Faker();
        Random rand = new Random();

        String path = Reader.class.getResource("/_books.txt").getPath();
        File fileName = new File(path);
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(int i=0;i<numberOfBooks;i++){
            String details = "";
            int aCount = rand.nextInt(1,4);
            details += IntStream.range(0,aCount).mapToObj(x->faker.book().author()).collect(Collectors.joining(","));
            details += "\t";

            int gCount = rand.nextInt(0,4);
            details += IntStream.rangeClosed(0,aCount).mapToObj(x->faker.book().genre()).collect(Collectors.joining(","));
            details += "\t";

            int pCount = rand.nextInt(0,4);
            details += IntStream.rangeClosed(0,pCount).mapToObj(x->faker.book().publisher()).collect(Collectors.joining(","));
            details += "\t";

            details += 17 * rand.nextInt(2,54);
            details += "\t";

            details += faker.book().title() + "\n";
            printWriter.write(details);
            //System.out.println("details = " + details);
        }
        printWriter.close();
    }
}