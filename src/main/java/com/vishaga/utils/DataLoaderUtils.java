package com.vishaga.utils;

import com.vishaga.model.*;
import org.example.Reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DataLoaderUtils {

    public static List<Movie> loadMovie(int limit){
        String path = Reader.class.getResource("/movie.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .limit(limit)
                    .map(Movie::from)
                    .toList();
        }catch (Exception ex){
            return List.of();
        }
    }

    public static List<Movie2> loadMovie2(int limit){
        String path = Reader.class.getResource("/movies2.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .limit(limit)
                    .map(Movie2::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Employee> loadEmployee(int limit){
        String path = Reader.class.getResource("/employee.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .limit(limit)
                    .map(Employee::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Country> loadCountries(){
        String path = Reader.class.getResource("/countries.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Country::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Population> loadPopulation(){
        String path = Reader.class.getResource("/city_population.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Population::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<University> loadUniversity(){
        String path = Reader.class.getResource("/universities.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(University::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }
}
