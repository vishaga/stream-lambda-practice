package com.vishaga.utils;

import com.vishaga.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoaderUtils {

    public static List<Movie> loadMovie(int limit){
        String path = DataLoaderUtils.class.getResource("/movie.txt").getPath();
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
        String path = DataLoaderUtils.class.getResource("/_movies.txt").getPath();
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
        String path = DataLoaderUtils.class.getResource("/_employee.txt").getPath();
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
        String path = DataLoaderUtils.class.getResource("/countries.txt").getPath();
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
        String path = DataLoaderUtils.class.getResource("/_city_population.txt").getPath();
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
        String path = DataLoaderUtils.class.getResource("/universities.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(University::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Book> loadBooks(){
        String path = DataLoaderUtils.class.getResource("/_books.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Book::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<String> loadLines(){
        String path = DataLoaderUtils.class.getResource("/sentence.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Article> loadArticles(){
        String path = DataLoaderUtils.class.getResource("/_articles.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Article::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static Map<LocalDate, Map<String, Double>> loadCurrencyConversion(){
        String path = DataLoaderUtils.class.getResource("/_currency.txt").getPath();
        Map<LocalDate, Map<String, Double>> currencyMap = new HashMap<>();
        try(Stream<String> s = Files.lines(Path.of(path))){
            currencyMap.put(
                    LocalDate.now(),
                    s.skip(1)
                            .map(l -> l.split("="))
                            .collect(
                                    Collectors.toMap(
                                            arr -> arr[0],
                                            arr -> Double.parseDouble(arr[1]))));

        }catch (Exception ex){
            System.out.println("ex = " + ex);
            currencyMap.put(LocalDate.now(), new HashMap<>());
        }
        return currencyMap;
    }

    public static List<City> loadCities(){
        String path = DataLoaderUtils.class.getResource("/_city_and_rank.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(City::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<NobelInfo> loadNobelWinner(){
        String path = DataLoaderUtils.class.getResource("/_nobel_winner.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(NobelInfo::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<College> loadColleges(){
        String path = DataLoaderUtils.class.getResource("/_us_colleges.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(College::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }
}
