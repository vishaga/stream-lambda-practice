package com.vishaga.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithBasicStreamTest {

    private static final List<String> alphabet =
            List.of(
                    "alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

    @Test
    @DisplayName("Words of length 6")
    public void wordsOfLength6() {
        List<String> wordsOfLength6 = alphabet.stream()
                .filter(word -> word.length() == 6)
                .map(String::toUpperCase)
                .toList();

        assertThat(wordsOfLength6).isEqualTo(
                List.of("JULIET", "QUEBEC", "SIERRA", "VICTOR", "YANKEE")
        );
    }

    @Test
    @DisplayName("Words of length between 6 and 9 (both exclusive)")
    public void filterPredicate() {
        Predicate<String> predicate = word -> word.length() > 6;
        predicate = predicate.and(word -> word.length() < 9);
        List<String> wordsOfLengthBetween6And9 = alphabet.stream()
                .filter(predicate)
                .map(String::toUpperCase)
                .toList();

        assertThat(wordsOfLengthBetween6And9).isEqualTo(
                List.of("CHARLIE", "FOXTROT", "NOVEMBER", "UNIFORM", "WHISKEY")
        );
    }

    @Test
    @DisplayName("Group all words by its length using toMap")
    public void groupingBy_1() {
        Map<Integer, List<String>> wordsGroupedByLength = alphabet.stream()
                .collect(
                        Collectors.toMap(
                                String::length,                     // KeyMapper
                                a -> new ArrayList<>(List.of(a)),   // ValueMapper
                                (a, b) -> {
                                    a.addAll(b);                    // Combiner or value Merger
                                    return a;
                                }
                        )
                );

        assertThat(wordsGroupedByLength).containsExactly(
                entry(4, List.of("alfa", "echo", "golf", "kilo", "lima", "mike", "papa", "zulu")),
                entry(5, List.of("bravo", "delta", "hotel", "india", "oscar", "romeo", "tango", "x-ray")),
                entry(6, List.of("juliet", "quebec", "sierra", "victor", "yankee")),
                entry(7, List.of("charlie", "foxtrot", "uniform", "whiskey")),
                entry(8, List.of("november"))
        );
    }

    @Test
    @DisplayName("Group all words by its length using groupingBy")
    public void groupingBy_2() {
        Map<Integer, List<String>> wordsGroupedByLength = alphabet.stream()
                .collect(Collectors.groupingBy(String::length));

        assertThat(wordsGroupedByLength).containsExactly(
                entry(4, List.of("alfa", "echo", "golf", "kilo", "lima", "mike", "papa", "zulu")),
                entry(5, List.of("bravo", "delta", "hotel", "india", "oscar", "romeo", "tango", "x-ray")),
                entry(6, List.of("juliet", "quebec", "sierra", "victor", "yankee")),
                entry(7, List.of("charlie", "foxtrot", "uniform", "whiskey")),
                entry(8, List.of("november"))
        );
    }

    @Test
    @DisplayName("Group all words by its length using groupingBy with counting")
    public void groupingBy_3() {

        // Counting returns value of Long type
        Map<Integer, Long> wordsGroupedByLength = alphabet.stream()
                .collect(
                        Collectors.groupingBy(
                                String::length,
                                Collectors.counting()));

        assertThat(wordsGroupedByLength).containsExactly(
                entry(4, 8L),
                entry(5, 8L),
                entry(6, 5L),
                entry(7, 4L),
                entry(8, 1L)
        );
    }

    @Test
    @DisplayName("Group all words by its length using groupingBy with counting (Int)")
    public void groupingBy_4() {

        // Counting returns value of Long type which can than collected and mapped to Integer using collectingAndThan()
        Map<Integer, Integer> wordsGroupedByLength = alphabet.stream()
                .collect(
                        Collectors.groupingBy(
                                String::length,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue)));

        assertThat(wordsGroupedByLength).containsExactly(
                entry(4, 8),
                entry(5, 8),
                entry(6, 5),
                entry(7, 4),
                entry(8, 1)
        );
    }
}
