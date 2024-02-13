package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.vishaga.model.*;
import com.vishaga.utils.MockData;
import com.vishaga.utils.WriterUtil;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        List<Car> cars = MockData.cars();
        System.out.println("cars = " + cars);
        //pullEmployee();
        //WriterUtil.write(cars, "sample_file.txt");
        System.out.println("Done");

    }

    private static void pullEmployee() throws Exception{
        HttpClient build = HttpClient.newBuilder().build();

        HttpRequest r = HttpRequest.newBuilder()
                //.uri(URI.create("https://hub.dummyapis.com/employee?noofRecords=1000&idStarts=29001"))
                .GET()
                .build();

        HttpResponse<String> str = build.send(r, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        Employee1[] employee = mapper.readValue(str.body(), Employee1[].class);
        Employee emp = Employee.from(employee[0].toString());
        WriterUtil.write(Arrays.asList(employee), "sample_file.txt");
    }
}