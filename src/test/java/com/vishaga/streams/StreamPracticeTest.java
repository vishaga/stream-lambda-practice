package com.vishaga.streams;

import com.vishaga.java.util.Function;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class StreamPracticeTest {

    @BeforeAll
    public static void setUp(){
    }

    @Test
    @DisplayName("Given a list of integers, find the sum of all even numbers.")
    public void practiceTest_1(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum = numbers.stream()
                .filter(number -> number % 2 == 0)
                .mapToInt(number -> number)
                .sum();

        assertThat(sum).isEqualTo(30);
    }

    @Test
    @DisplayName("Given a list of strings, convert each string to uppercase and collect the result into a new list.")
    public void practiceTest_2(){
        List<String> words = List.of("apple", "banana", "orange", "grape");

        List<String> upperCaseWords = words.stream()
                .map(String::toUpperCase)
                .toList();

        assertThat(upperCaseWords).contains("APPLE","BANANA", "ORANGE", "GRAPE");
    }

    @Test
    @DisplayName("Given a list of words, count the number of words that start with the letter 'a'.")
    public void practiceTest_3(){
        List<String> words = List.of("apple", "banana", "avocado", "grape", "apricot");

        long wordsStartingWithA = words.stream()
                .filter(word -> word.startsWith("a"))
                .count();

        assertThat(wordsStartingWithA).isEqualTo(3L);
    }

    @Test
    @DisplayName("Given a list of employees with their salaries, find the average salary.")
    public void practiceTest_4(){
        record Employee(String name, int salary){};

        List<Employee> employees = List.of(
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
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

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

        List<Book> books = List.of(
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
        List<String> words = List.of("apple", "banana", "kiwi", "grape", "peach");


        String newString = words.stream()
                .filter(word -> word.length() > 4)
                .collect(Collectors.joining(","));

        assertThat(newString).isEqualTo("apple,banana,grape,peach");
    }

    @Test
    @DisplayName("Given a list of words, create a list of all unique characters present in those words.")
    public void practiceTest_8(){
        List<String> words = List.of("hello", "world", "java", "stream");

        Set<String> uniqueCharacters = words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .collect(Collectors.toSet());

        assertThat(uniqueCharacters).contains("h","e","l","o","w","r","d","j","a","v","s","t","m");
    }

    @Test
    @DisplayName("Given a list of numbers, partition them into two groups - even and odd.")
    public void practiceTest_9(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

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
        List<Integer> numbers = List.of(15, 8, 20, 12, 7, 18);

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

        List<Student> students = List.of(
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
        List<String> list1 = List.of("apple", "banana", "orange");
        List<String> list2 = List.of("banana", "kiwi", "grape");

        List<String> words = Stream.concat(list1.stream(), list2.stream())
                .toList();

        List<String> uniqueWords = words.stream().distinct().toList();

        assertThat(uniqueWords).contains("apple", "banana", "orange", "kiwi", "grape");
    }

    @Test
    @DisplayName("Given two lists of strings, combine them into a single list and remove duplicate strings.")
    public void practiceTest_13(){
        List<String> list1 = List.of("apple", "banana", "orange");
        List<String> list2 = List.of("banana", "kiwi", "grape");

        List<String> words = Stream.concat(list1.stream(), list2.stream())
                .toList();

        List<String> uniqueWords = words.stream().distinct().toList();

        assertThat(uniqueWords).contains("apple", "banana", "orange", "kiwi", "grape");
    }

    @Test
    @DisplayName("Given a list of transactions, find the total value of transactions for a specific category ('Electronics').")
    public void practiceTest_14(){
        record  Transaction(String category, int value){};

        List<Transaction> transactions = List.of(
                new Transaction("Electronics", 1200),
                new Transaction("Grocery", 300),
                new Transaction("Electronics", 800),
                new Transaction("Clothing", 200),
                new Transaction("Electronics", 1500)
        );

        Map<String, Integer> totalByCategory = transactions.stream()
                .collect(
                        Collectors.groupingBy(
                                Transaction::category,
                                Collectors.summingInt(Transaction::value)));

        assertThat(totalByCategory).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("Electronics", 3500),
                        Map.entry("Grocery", 300),
                        Map.entry("Clothing", 200)
                )
        );
    }

    @Test
    @DisplayName("Given a list of employees, group them by department and find the average salary for each department.")
    public void practiceTest_15(){
        record  Employee(String name, int salary, String department){};

        List<Employee> employees = List.of(
                new Employee("John", 50000, "IT"),
                new Employee("Alice", 60000, "Finance"),
                new Employee("Bob", 75000, "IT"),
                new Employee("Charlie", 80000, "Finance"),
                new Employee("Eva", 70000, "HR"),
                new Employee("David", 90000, "HR")
        );

        Map<String, Double> avgSalaryByDepartment = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::department,
                                Collectors.collectingAndThen(
                                        Collectors.summarizingInt(Employee::salary),
                                        IntSummaryStatistics::getAverage)));

        assertThat(avgSalaryByDepartment).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("IT", 62_500d),
                        Map.entry("Finance", 70_000d),
                        Map.entry("HR", 80_000d)
                )
        );
    }

    @Test
    @DisplayName("Given a list of orders, find the total quantity of items sold for each product.")
    public void practiceTest_16(){
        record  Order(String product, int itemSold){};

        List<Order> orders = List.of(
                new Order("Apple", 3),
                new Order("Banana", 2),
                new Order("Apple", 5),
                new Order("Orange", 4),
                new Order("Banana", 1)
        );

        Map<String, Integer> totalSoldByProduct = orders.stream()
                .collect(
                        Collectors.groupingBy(
                                Order::product,
                                Collectors.summingInt(Order::itemSold)));

        assertThat(totalSoldByProduct).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("Apple", 8),
                        Map.entry("Banana", 3),
                        Map.entry("Orange", 4)
                )
        );
    }

    @Test
    @DisplayName("Given a list of people, partition them into two groups - adults (age 18 and above) and minors (below 18). Count the number of people in each group.")
    public void practiceTest_17(){
        record  Person(String name, int age){};

        List<Person> people = List.of(
                new Person("Alice", 25),
                new Person("Bob", 16),
                new Person("Charlie", 30),
                new Person("Diana", 14),
                new Person("Eva", 22)
        );

        Map<Boolean, Long> allPersonByMinorAndAdult = people.stream()
                .collect(
                        Collectors.partitioningBy(
                                person -> person.age < 18,
                                Collectors.counting()));

        assertThat(allPersonByMinorAndAdult).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry(true, 2L),
                        Map.entry(false, 3L)
                )
        );
    }

    @Test
    @DisplayName("Given a list of lists of integers, flatten the list and find the product of all distinct numbers.")
    public void practiceTest_18(){

        List<List<Integer>> nestedLists = Arrays.asList(
                Arrays.asList(2, 3, 4),
                Arrays.asList(3, 4, 5),
                Arrays.asList(5, 6, 7)
        );

        // Using flatMap
        Integer productOfDistinctElements_1 = nestedLists.stream()
                .flatMap(Collection::stream)
                .distinct()
                .reduce(1, Math::multiplyExact);

        assertThat(productOfDistinctElements_1).isEqualTo(5040);

        // Using mapMulti
        Integer productOfDistinctElements_2 = nestedLists.stream()
                //.mapMulti(Iterable<Integer>::forEach)
                .<Integer>mapMulti((subList, downStream) -> subList.forEach(downStream::accept))
                .distinct()
                .reduce(1, Math::multiplyExact);

        assertThat(productOfDistinctElements_2).isEqualTo(5040);

        // TODO: Also try collectors.mapping()
    }

}
