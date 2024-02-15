package com.vishaga.streams;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

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

    @Test
    @DisplayName("Given a list of books, sort them first by genre in ascending order, and then by the number of pages in descending order. Return the top 3 books.")
    public void practiceTest_19(){
        record Book(String genre, String name, int page){};

        List<Book> books = List.of(
                new Book("Sci-Fi", "Dune", 500),
                new Book("Fantasy", "Harry Potter", 700),
                new Book("Sci-Fi", "Foundation", 600),
                new Book("Mystery", "Sherlock Holmes", 400),
                new Book("Fantasy", "Lord of the Rings", 900),
                new Book("Mystery", "Murder on the Orient Express", 350)
        );

        List<String> top3Books = books.stream()
                .sorted(
                        Comparator.comparing(Book::genre)
                                .thenComparing(Book::page,
                                        Comparator.reverseOrder()))
                .limit(3)
                .map(Book::name)
                .toList();

        assertThat(top3Books).contains("Lord of the Rings","Harry Potter","Sherlock Holmes");
    }

    @Test
    @DisplayName("Given a list of transactions, find the transaction with the highest value using a custom reduction operation.")
    public void practiceTest_20(){
        record  Transaction(String category, int value){};

        List<Transaction> transactions = List.of(
                new Transaction("Electronics", 1200),
                new Transaction("Grocery", 300),
                new Transaction("Electronics", 800),
                new Transaction("Clothing", 200),
                new Transaction("Electronics", 1500)
        );

        Optional<Transaction> highestValueTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.value() > t2.value() ? t1 : t2);

        assertThat(highestValueTransaction).isNotEqualTo(Optional.empty());
        assertThat(highestValueTransaction.get().category).isEqualTo("Electronics");
        assertThat(highestValueTransaction.get().value).isEqualTo(1500);
    }

    @Test
    @DisplayName("Given a list of orders, extract the items from each order, flatten the list of items, and count the occurrences of each item.")
    public void practiceTest_21(){
        record  Order(String... items){};

        List<Order> orders = Arrays.asList(
                new Order("Apple", "Banana", "Orange"),
                new Order("Banana", "Kiwi", "Grape"),
                new Order("Apple", "Kiwi", "Orange", "Grape")
        );

        Map<String, Integer> ordersByItems = orders.stream()
                .flatMap(order -> Stream.of(order.items))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue)));

        assertThat(ordersByItems).containsAllEntriesOf(
                Map.ofEntries(
                        entry("Apple",2),
                        entry("Banana",2),
                        entry("Orange",2),
                        entry("Kiwi",2),
                        entry("Grape",2)

                )
        );
    }

    @Test
    @DisplayName("Given a list of numbers, find the product of the square of each even number.")
    public void practiceTest_22(){

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        OptionalInt productOfSquareOfEvenNumbers = numbers.stream()
                .filter(number -> number % 2 == 0)
                .mapToInt(number -> number * number)
                .reduce(Math::multiplyExact);

        assertThat(productOfSquareOfEvenNumbers).isNotEqualTo(OptionalInt.empty());
        assertThat(productOfSquareOfEvenNumbers.getAsInt()).isEqualTo(14_745_600);
    }

    @Test
    @DisplayName("Given a list of movies, sort them by release year in descending order and limit the result to the top 5 movies.")
    public void practiceTest_23(){
        record Movie(String name, int releaseYear){};

        List<Movie> movies = Arrays.asList(
                new Movie("Inception", 2010),
                new Movie("The Dark Knight", 2008),
                new Movie("Interstellar", 2014),
                new Movie("Fight Club", 1999),
                new Movie("The Shawshank Redemption", 1994),
                new Movie("The Matrix", 1999)
        );

        List<String> top5Movies = movies.stream()
                .sorted(Comparator.comparing(Movie::releaseYear, Comparator.reverseOrder()))
                .limit(5)
                .map(Movie::name)
                .toList();

        assertThat(top5Movies.isEmpty()).isFalse();
        assertThat(top5Movies).contains("Interstellar", "Inception", "The Dark Knight", "Fight Club", "The Matrix");
    }

    @Test
    @DisplayName("Given a list of books, group them by author and find the average number of pages for each author's books.")
    public void practiceTest_24(){
        record Book(String author, String name, int pages){};

        List<Book> books = Arrays.asList(
                new Book("Isaac Asimov", "Foundation", 500),
                new Book("J.K. Rowling", "Harry Potter", 700),
                new Book("Isaac Asimov", "Robot Series", 600),
                new Book("Agatha Christie", "Murder on the Orient Express", 350),
                new Book("J.R.R. Tolkien", "Lord of the Rings", 900)
        );

        Map<String, Double> authorsByAveragePagesWritten = books.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::author,
                                Collectors.collectingAndThen(
                                        Collectors.summarizingInt(Book::pages),
                                        IntSummaryStatistics::getAverage)));

        assertThat(authorsByAveragePagesWritten.isEmpty()).isFalse();
        assertThat(authorsByAveragePagesWritten).containsAllEntriesOf(
                Map.ofEntries(
                        entry("Isaac Asimov", 550d),
                        entry("J.K. Rowling", 700d),
                        entry("Agatha Christie", 350d),
                        entry("J.R.R. Tolkien", 900d)
                )
        );
    }

    @Test
    @DisplayName("Given two lists of employees, find the common employees based on their names and collect them into a new list.")
    public void practiceTest_25(){
        record Employee(String name, int salary){
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Employee employee = (Employee) o;
                return Objects.equals(name, employee.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name);
            }
        };

        List<Employee> list1 = Arrays.asList(
                new Employee("John", 50000),
                new Employee("Alice", 60000),
                new Employee("Bob", 75000),
                new Employee("Charlie", 80000)
        );

        List<Employee> list2 = Arrays.asList(
                new Employee("Bob", 72000),
                new Employee("Alice", 60000),
                new Employee("David", 85000),
                new Employee("Eva", 70000)
        );

        List<Employee> commonEmployees = new ArrayList<>(list1);
        // overriding of equals and hashcode is important so that check can happen only on names.
        commonEmployees.retainAll(list2);
        List<String> commonNames = commonEmployees.stream().map(Employee::name).toList();

        assertThat(commonNames.isEmpty()).isFalse();
        assertThat(commonNames).contains("Bob", "Alice");
    }

    @Test
    @DisplayName("Given a large list of numbers, find the sum of squares using parallel streams.")
    public void practiceTest_26(){

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1_000_000; i++) {
            numbers.add(i);
        }

        long sum1 = numbers.stream().mapToLong(n -> (long) n * n).sum();
        long sum2 = numbers.parallelStream().mapToLong(n -> (long) n * n).sum();
        assertThat(sum1).isEqualTo(333333833333500000L);
        assertThat(sum1).isEqualTo(sum2);
    }

    @Test
    @DisplayName("Given a list of sentences, find the average length of words for sentences that contain the word 'Java'.")
    public void practiceTest_27(){

        List<String> sentences = Arrays.asList(
                "Java is a powerful programming language.",
                "Python is widely used for data science.",
                "Java applications can run on any device.",
                "Functional programming is gaining popularity."
        );

        int averageLengthOfWordsInSentenceContainingJavaKeyword = (int) sentences.stream()
                .filter(line -> line.contains("Java"))
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .mapToInt(String::length)
                .summaryStatistics().getAverage();

        assertThat(averageLengthOfWordsInSentenceContainingJavaKeyword).isEqualTo(5);
    }

    @Test
    @DisplayName("Given a list of students co-owning a set of books (books are shared between multiple students)," +
            "find the book and mapped student(s).")
    public void practiceTest_28(){

        record Book(String name, int id){};
        record Student(String name, int age, List<Book> books){};

        Book book1 = new Book("Book1", 1);
        Book book2 = new Book("Book2", 2);
        Book book3 = new Book("Book3", 3);
        Book book4 = new Book("Book4", 4);

        List<Student> students = List.of(
                new Student("Student1", 20, List.of(book1, book2, book3)),
                new Student("Student2", 22, List.of(book2, book3, book4)),
                new Student("Student3", 21, List.of(book1, book4))
        );

        record Tuple(Book book, Student student){};

        Map<String, Set<String>> bookToStudents = students.stream()
                //.flatMap(student -> student.books().stream().map(book -> new Tuple(book, student)))
                .<Tuple>mapMulti((student, downstream) -> student.books().forEach(book -> downstream.accept(new Tuple(book, student)))) //Stream<Tuple>
                .collect(
                        // Collectors.groupingBy(Tuple::book, Collectors.toSet())
                        Collectors.groupingBy(
                                tuple -> tuple.book().name,
                                Collectors.mapping(
                                        tuple -> tuple.student().name(),
                                        Collectors.toSet())));

        assertThat(bookToStudents).containsAllEntriesOf(
                Map.ofEntries(
                        entry("Book1",Set.of("Student1", "Student3")),
                        entry("Book2",Set.of("Student1", "Student2")),
                        entry("Book3",Set.of("Student1", "Student2")),
                        entry("Book4",Set.of("Student2", "Student3"))
                ));
    }

    @Test
    @DisplayName("Find duplicate elements from given array.")
    public void practiceTest_29(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 2, 5, 6, 3, 7, 8, 8, 9, 10);

        // Approach 1: Using Map Count.
        List<Integer> duplicateElements = numbers.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()),
                                map -> map.entrySet().stream()
                                        .filter(entry -> entry.getValue() > 1)
                                        .map(Map.Entry::getKey)
                                        .toList()));

        assertThat(duplicateElements).contains(2,3,8);

        // Approach 2: Using Additional DataStructure Set.
        Set<Integer> added = new HashSet<>();
        duplicateElements = numbers.stream()
                .filter(number -> !added.add(number))
                .toList();

        assertThat(duplicateElements).contains(2,3,8);
    }

    @Test
    @DisplayName("allMatch(): allMatch() is terminal short circuit operation, once condition mismatched, it terminates.")
    public void practiceTest_30(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 0, 6, 7, 8, 9, 10);
        int[] counter = {0};
        Predicate<Integer> predicate = number -> {
            counter[0]++;
            return number > 0;
        };
        boolean numbersGreaterThanZero = numbers.stream().allMatch(predicate);

        assertThat(numbersGreaterThanZero).isFalse();
        assertThat(counter[0]).isEqualTo(6);
    }

    @Test
    @DisplayName("noneMatch(): noneMatch() is terminal short circuit operation, once condition matched, it terminates.")
    public void practiceTest_31(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 0, 6, 7, 8, 9, 10);
        int[] counter = {0};
        Predicate<Integer> predicate = number -> {
            counter[0]++;
            return number > 7;
        };
        boolean numbersGreaterThanZero = numbers.stream().noneMatch(predicate);

        assertThat(numbersGreaterThanZero).isFalse();
        assertThat(counter[0]).isEqualTo(9);
    }

    @Test
    @DisplayName("anyMatch(): anyMatch() is terminal short circuit operation, once condition matched, it terminates.")
    public void practiceTest_32(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 0, 6, 7, 8, 9, 10);
        int[] counter = {0};
        Predicate<Integer> predicate = number -> {
            counter[0]++;
            return number > 7;
        };
        boolean numbersGreaterThanZero = numbers.stream().anyMatch(predicate);

        assertThat(numbersGreaterThanZero).isTrue();
        assertThat(counter[0]).isEqualTo(9);
    }

    @Test
    @DisplayName("Given a list of people, partition them based on sex(M/F), Sum their ages.")
    public void practiceTest_33(){
        record  Person(String name, String sex, int age){};

        List<Person> people = List.of(
                new Person("Alice", "F",25),
                new Person("Bob", "M",16),
                new Person("Charlie", "M",30),
                new Person("Diana", "F",14),
                new Person("Eva", "F",22)
        );

        Map<Boolean, Integer> totalAgeBySex = people.stream()
                .collect(
                        Collectors.partitioningBy(
                                person -> person.sex.equals("M"),
                                Collectors.summingInt(Person::age)));

        assertThat(totalAgeBySex).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry(true, 46),
                        Map.entry(false, 61)
                )
        );
    }

    @Test
    @DisplayName("dropWhile(): Given a list of number, drop all elements unless condition is met (short circuit).")
    public void practiceTest_34(){
        List<Integer> numbers = List.of(10,20,30,40,50,60,70,80,90,100,110,120,130,140);

        List<Integer> numbersGreaterThan100 = numbers.stream().dropWhile(number -> number <= 100).toList();
        assertThat(numbersGreaterThan100).containsExactly(110, 120, 130, 140);

        List<Integer>  result = numbers.stream().dropWhile(number -> number > 100).toList();
        assertThat(result).containsExactly(10,20,30,40,50,60,70,80,90,100,110,120,130,140);
    }

    @Test
    @DisplayName("takeWhile(): Given a list of number, take elements until the condition is mismatched (short circuit).")
    public void practiceTest_35(){
        List<Integer> numbers = List.of(10,20,30,40,50,60,70,80,90,100,110,120,130,140);

        List<Integer> numbersLesserThan100 = numbers.stream().takeWhile(number -> number <= 100).toList();
        assertThat(numbersLesserThan100).containsExactly(10,20,30,40,50,60,70,80,90,100);
    }
}
