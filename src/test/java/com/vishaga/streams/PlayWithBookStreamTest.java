package com.vishaga.streams;

import com.vishaga.model.Book;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithBookStreamTest {

    private static List<Book> BOOKS;

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
    @DisplayName("Author with max books")
    public void authorsWithMaxBooks(){
        Map<String, Long> collect = BOOKS.stream()
                .flatMap(book -> book.authors().stream())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        System.out.println("collect = " + collect);

        Map<String, Long> collect1 = BOOKS.stream()
                //.flatMap(book -> book.authors().stream())
                .collect(
                        Collectors.flatMapping(book -> book.authors().stream(),
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        )

                );
        System.out.println("collect = " + collect1);

        Map<Object, Long> collect2 = BOOKS.stream()
                .mapMulti((book, downstream) -> book.authors().forEach(downstream))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        System.out.println("collect = " + collect2);

        List<String> dataList = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple", "orange");

        // Grouping by element and finding the element with the max count in each group
        Map<String, String> result = dataList.stream()
                .collect(Collectors.groupingBy(
                        s -> s, // grouping by the element itself
                        Collectors.collectingAndThen(
                                Collectors.counting(), // counting occurrences
                                count -> dataList.stream()
                                        //.filter(s -> s.equals(count.getKey())) // filter by the grouped element
                                        .collect(Collectors.groupingBy(
                                                s -> s,
                                                Collectors.counting()
                                        ))
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElse(null)
                        )
                ));

        // Printing the result
        result.forEach((key, value) -> System.out.println(key + ": " + value));

    }

    @Test
    @DisplayName("Author with max books")
    public void authorsWithMaxBooks213() {
        Map<Object, Long> collect = BOOKS.stream()
                .mapMulti(((book, consumer) -> book.authors().forEach(consumer)))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.mapping(a -> {
                                            System.out.println(a + " : " + a.getClass());
                                            return Function.identity();
                                        },
                                        Collectors.counting())
                        ));

        System.out.println("collect = " + collect);
    }
}
