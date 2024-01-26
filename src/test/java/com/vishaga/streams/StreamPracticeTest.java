package com.vishaga.streams;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class StreamPracticeTest {

    @BeforeAll
    public static void setUp(){
        //COUNTRIES = DataLoaderUtils.loadCountries();
    }

    @Test
    @DisplayName("Given a list of integers, find the sum of all even numbers.")
    public void practiceTest_1(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum = numbers.stream()
                .filter(number -> number % 2 == 0)
                .mapToInt(number -> number)
                .sum();

        assertThat(sum).isEqualTo(30);
    }

    @Test
    @DisplayName("Given a list of strings, convert each string to uppercase and collect the result into a new list.")
    public void practiceTest_2(){
        List<String> words = Arrays.asList("apple", "banana", "orange", "grape");

        List<String> upperCaseWords = words.stream()
                .map(String::toUpperCase)
                .toList();

        assertThat(upperCaseWords).contains("APPLE","BANANA", "ORANGE", "GRAPE");
    }

    @Test
    @DisplayName("Given a list of words, count the number of words that start with the letter 'a'.")
    public void practiceTest_3(){
        List<String> words = Arrays.asList("apple", "banana", "avocado", "grape", "apricot");

        long wordsStartingWithA = words.stream()
                .filter(word -> word.startsWith("a"))
                .count();

        assertThat(wordsStartingWithA).isEqualTo(3L);
    }

    @Test
    @DisplayName("Given a list of employees with their salaries, find the average salary.")
    public void practiceTest_4(){
        record Employee(String name, int salary){};

        List<Employee> employees = Arrays.asList(
                new Employee("John", 50000),
                new Employee("Alice", 60000),
                new Employee("Bob", 75000),
                new Employee("Charlie", 80000)
        );

        double averageSalary = employees.stream()
                .mapToInt(employee -> employee.salary)
                .summaryStatistics().getAverage();

        assertThat(averageSalary).isEqualTo(66_250L);
    }

    @Test
    @DisplayName("Given a list of numbers, filter out the even numbers, square each remaining number, and then find the sum.")
    public void practiceTest_5(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        double squareOfOddNumbers = numbers.stream()
                .filter(number -> number % 2 != 0)
                .mapToInt(number -> number * number)
                .sum();

        assertThat(squareOfOddNumbers).isEqualTo(165L);
    }

    @Test
    @DisplayName("Given a list of books, group them by genre and count the number of books in each genre.")
    public void practiceTest_6(){
        record Book(String genre, String name){};

        List<Book> books = Arrays.asList(
                new Book("Sci-Fi", "Dune"),
                new Book("Fantasy", "Harry Potter"),
                new Book("Sci-Fi", "Foundation"),
                new Book("Mystery", "Sherlock Holmes"),
                new Book("Fantasy", "Lord of the Rings"),
                new Book("Mystery", "Murder on the Orient Express"),
                new Book("Programming", "Head First Java")
        );
        Map<String, Long> booksByGenre = books.stream()
                .collect(Collectors.groupingBy(Book::genre, Collectors.counting()));

        assertThat(booksByGenre).containsAllEntriesOf(
                Map.ofEntries(
                        entry("Sci-Fi",2L),
                        entry("Fantasy",2L),
                        entry("Mystery",2L),
                        entry("Programming",1L)
                )
        );
    }

    @Test
    @DisplayName("Given a list of strings, filter out the strings with length less than 4 " +
            "and join the remaining strings into a single string, separated by commas.")
    public void practiceTest_7(){
        List<String> words = Arrays.asList("apple", "banana", "kiwi", "grape", "peach");


        String newString = words.stream()
                .filter(word -> word.length() > 4)
                .collect(Collectors.joining(","));

        assertThat(newString).isEqualTo("apple,banana,grape,peach");
    }

    @Test
    @DisplayName("Given a list of words, create a list of all unique characters present in those words.")
    public void practiceTest_8(){
        List<String> words = Arrays.asList("hello", "world", "java", "stream");

        Set<String> uniqueCharacters = words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .collect(Collectors.toSet());

        assertThat(uniqueCharacters).contains("h","e","l","o","w","r","d","j","a","v","s","t","m");
    }

    @Test
    @DisplayName("Given a list of numbers, partition them into two groups - even and odd.")
    public void practiceTest_9(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Map<Boolean, List<Integer>> evenOddNumbers = numbers.stream()
                .collect(Collectors.partitioningBy(num -> num % 2 == 0));

        assertThat(evenOddNumbers).containsAllEntriesOf(
                Map.ofEntries(
                        entry(true,List.of(2,4,6,8,10)),
                        entry(false,List.of(1,3,5,7,9))
                )
        );
    }

    @Test
    @DisplayName("Given a list of integers, find the maximum value using the reduce operation. " +
            "Return an Optional to handle the case where the list is empty.")
    public void practiceTest_10(){
        List<Integer> numbers = Arrays.asList(15, 8, 20, 12, 7, 18);

        Optional<Integer> maxNumber = numbers.stream()
                .reduce(Math::max);

        assertThat(maxNumber.get()).isEqualTo(20);

        Optional<Integer> empty = Stream.<Integer>of()
                .reduce(Math::max);

        assertThat(empty).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Given a list of students with their scores, sort the students " +
            "based on their scores in descending order and limit the result to the top 3 students.")
    public void practiceTest_11(){

        record Student(String name, int score){};

        List<Student> students = Arrays.asList(
                new Student("Alice", 85),
                new Student("Bob", 92),
                new Student("Charlie", 78),
                new Student("Diana", 96),
                new Student("Eva", 88)
        );
        List<String> top3Students = students.stream()
                .sorted(Comparator.comparing(Student::score).reversed())
                .map(Student::name)
                .limit(3).toList();

        assertThat(top3Students).contains("Diana", "Bob", "Eva");
    }

    @Test
    @DisplayName("Given two lists of strings, combine them into a single list and remove duplicate strings.")
    public void practiceTest_12(){
        List<String> list1 = Arrays.asList("apple", "banana", "orange");
        List<String> list2 = Arrays.asList("banana", "kiwi", "grape");

        List<String> words = Stream.concat(list1.stream(), list2.stream())
                .toList();

        List<String> uniqueWords = words.stream().distinct().toList();

        assertThat(uniqueWords).contains("apple", "banana", "orange", "kiwi", "grape");
    }

    @Test
    @DisplayName("Given two lists of strings, combine them into a single list and remove duplicate strings.")
    public void practiceTest_13(){
        List<String> list1 = Arrays.asList("apple", "banana", "orange");
        List<String> list2 = Arrays.asList("banana", "kiwi", "grape");

        List<String> words = Stream.concat(list1.stream(), list2.stream())
                .toList();

        List<String> uniqueWords = words.stream().distinct().toList();

        assertThat(uniqueWords).contains("apple", "banana", "orange", "kiwi", "grape");
    }

}
