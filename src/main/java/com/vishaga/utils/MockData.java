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

public class MockData {

    public static List<Movie> movies(int limit){
        String path = MockData.class.getResource("/_movie.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .limit(limit)
                    .map(Movie::from)
                    .toList();
        }catch (Exception ex){
            return List.of();
        }
    }

    public static List<Movie2> movies2(int limit){
        String path = MockData.class.getResource("/_movies.txt").getPath();
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

    public static List<Employee> employees(int limit){
        String path = MockData.class.getResource("/_employee.txt").getPath();
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

    public static List<Country> countries(){
        String path = MockData.class.getResource("/_countries.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Country::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Population> populationStats(){
        String path = MockData.class.getResource("/_city_population.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Population::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<University> universities(){
        String path = MockData.class.getResource("/_universities.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(University::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Book> books(){
        String path = MockData.class.getResource("/_books.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Book::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<String> lines(){
        String path = MockData.class.getResource("/_sentence.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Article> articles(){
        String path = MockData.class.getResource("/_articles.txt").getPath();
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
        String path = MockData.class.getResource("/_currency.txt").getPath();
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

    public static List<City> cities(){
        String path = MockData.class.getResource("/_city_and_rank.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(City::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<NobelInfo> nobelWinners(){
        String path = MockData.class.getResource("/_nobel_winner.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(NobelInfo::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<College> colleges(){
        String path = MockData.class.getResource("/_us_colleges.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(College::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<USUniversity> USUniversity(){
        String path = MockData.class.getResource("/_US_universities.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(USUniversity::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<Car> cars() {
        String path = MockData.class.getResource("/_cars.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(Car::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<ElectoralBondUser> electoralBondUserList() {
        String path = MockData.class.getResource("/_electoral_bond_user.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(ElectoralBondUser::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }

    public static List<ElectoralBondBuyer> electoralBondBuyerList() {
        String path = MockData.class.getResource("/_electoral_bond_buyer.txt").getPath();
        try(Stream<String> s = Files.lines(Path.of(path))){
            return s.skip(1)
                    .map(ElectoralBondBuyer::from)
                    .toList();
        }catch (Exception ex){
            System.out.println("ex = " + ex);
            return List.of();
        }
    }
}
