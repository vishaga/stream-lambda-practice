package com.vishaga.streams;

import com.vishaga.model.Book;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithBookStreamTest {

    private static List<Book> BOOKS;
    private static final Book FAKE = Book.fake();

    @BeforeAll
    public static void setUp(){
        BOOKS = DataLoaderUtils.loadBooks();
    }

    @Test
    @DisplayName("Count Books")
    public void countTest(){
        assertThat((long) BOOKS.size()).isEqualTo(1069);
    }

    @Test
    @DisplayName("Book with max Unit Sold")
    public void bookWithMaxUnitSold(){
        Book book = BOOKS.stream().max(Comparator.comparing(Book::unitSold)).orElse(FAKE);
        assertThat(book.name()).isEqualTo("God is Not Great: How Religion Poisons Everything");
        assertThat(book.unitSold()).isEqualTo(90456);
    }

    @Test
    @DisplayName("Publishers Gross Sales")
    public void grossSalesOfPublishers(){
        Map<String, Integer> publisherRevenueGroupedByPublisher = BOOKS.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::publisher,
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(Book::publisherRevenue),
                                        Double::intValue)));

        assertThat(publisherRevenueGroupedByPublisher).containsAllEntriesOf(
                Map.ofEntries(
                        entry("HarperCollins Christian Publishing", 2135),
                        entry("HarperCollins Publishers", 131309),
                        entry("Hachette Book Group", 137555),
                        entry("Amazon Digital Services,  Inc.", 148244),
                        entry("Random House LLC", 189585),
                        entry("Penguin Group (USA) LLC", 213817),
                        entry("Macmillan", 32356)
                )
        );
    }

    @Test
    @DisplayName("Any one the most cheapest Book")
    public void mostCheapestBook(){
        Book cheapestBookByPrice = BOOKS.stream()
                .min(Comparator.comparing(Book::price))
                .orElse(FAKE);
        assertThat(cheapestBookByPrice.name()).isEqualTo("Last Sacrifice");
    }

    @Test
    @DisplayName("All cheapest Book")
    public void allCheapestBook(){
        Map.Entry<Double, List<String>> allCheapestBooks = BOOKS.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Book::price,
                                        TreeMap::new,
                                        Collectors.mapping(
                                                Book::name,
                                                Collectors.toList())
                                ),
                                TreeMap::firstEntry));

        List<String> books = allCheapestBooks.getValue();

        assertThat(allCheapestBooks.getKey()).isEqualTo(0.99);
        assertThat(books).contains("Last Sacrifice", "Needful Things", "Dead to the World");
    }

    @Test
    @DisplayName("All Cheapest and Costliest Books")
    public void allCheapestAndCostliestBooks(){
        List<Map.Entry<Double, List<String>>> allCheapestAndCostliestBooks = BOOKS.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Book::price,
                                        TreeMap::new,
                                        Collectors.mapping(
                                                Book::name,
                                                Collectors.toList())),
                                map -> List.of(map.lastEntry(),map.firstEntry())));

        Map.Entry<Double, List<String>> costliestBooks = allCheapestAndCostliestBooks.get(0);
        Map.Entry<Double, List<String>> cheapestBooks = allCheapestAndCostliestBooks.get(1);

        assertThat(cheapestBooks.getKey()).isEqualTo(0.99);
        assertThat(costliestBooks.getKey()).isEqualTo(33.86);
        assertThat(cheapestBooks.getValue()).contains("Last Sacrifice", "Needful Things", "Dead to the World");
        assertThat(costliestBooks.getValue()).contains("The Wind in the Willows");
    }

    @Test
    @DisplayName("Author and number of books written or co-written.")
    public void authorsWithMaxBooks(){
        Map<String, Long> booksWrittenByAuthor1 = BOOKS.stream()
                //.flatMap(book -> book.authors().stream())
                .<String>mapMulti((book,downstream) -> book.authors().forEach(downstream))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()));

        assertThat(booksWrittenByAuthor1).containsAllEntriesOf(
                Map.ofEntries(
                        entry("A.A. Milne",1L),
                        entry("A.S.A. Harrison",1L),
                        entry("Adam Johnson",1L),
                        entry("James Joyce",3L),
                        entry("Wendelin Van Draanen",1L)
                ));

        Map<String, Long> booksWrittenByAuthor2 = BOOKS.stream()
                .collect(
                        Collectors.flatMapping(
                                book -> book.authors().stream(),
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting())));

        assertThat(booksWrittenByAuthor2).containsAllEntriesOf(
                Map.ofEntries(
                        entry("A.A. Milne",1L),
                        entry("A.S.A. Harrison",1L),
                        entry("Adam Johnson",1L),
                        entry("James Joyce",3L),
                        entry("Wendelin Van Draanen",1L)
                ));
    }

    @Test
    @DisplayName("Publishers with Gross-Sales and Publisher-Revenue")
    public void authorsWithGrossSalesAndRevenues() {
        Map<String, List<Integer>> grossSalesAndRevenuePerPublisher = BOOKS.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::publisher,
                                Collectors.teeing(
                                        Collectors.summingDouble(Book::grossSales),
                                        Collectors.summingDouble(Book::publisherRevenue),
                                        (grossSales,revenue) -> List.of(grossSales.intValue(),revenue.intValue()))));

        assertThat(grossSalesAndRevenuePerPublisher).containsAllEntriesOf(
                Map.ofEntries(
                        entry("HarperCollins Christian Publishing", List.of(3559,2135)),
                        entry("HarperCollins Publishers", List.of(218848,131309)),
                        entry("Hachette Book Group", List.of(229258,137555)),
                        entry("Amazon Digital Services,  Inc.", List.of(729809,148244)),
                        entry("Random House LLC", List.of(315975,189585)),
                        entry("Penguin Group (USA) LLC", List.of(356362,213817)),
                        entry("Macmillan", List.of(53927,32356))
                )
        );
    }
}
