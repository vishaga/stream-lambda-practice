package com.vishaga.streams;

import com.vishaga.model.City;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithCollectorOfTest {

    static List<City> CITIES;

    @BeforeAll
    public static void setUp(){
        CITIES = DataLoaderUtils.loadCities();
    }

    @Test
    @DisplayName("Using Collector.of: Suppose you want to collect a list of strings and concatenate them into a single string")
    public void test_1(){
        List<String> strings = List.of("Java", "is", "fun", "with", "Collector.of");

        String concatenatedString = strings.stream()
                .collect(
                        Collector.of(
                                StringBuilder::new, //Supplier
                                StringBuilder::append, // accumulator
                                StringBuilder::append, // combiner (for parallel streams)
                                StringBuilder::toString //finisher
                        )
                );

        assertThat(concatenatedString).isEqualTo("JavaisfunwithCollector.of");
    }

    @Test
    @DisplayName("Using Collector.of: Suppose you want to collect a list of integers and calculate their sum")
    public void test_2(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        Integer sum = numbers.stream()
                .collect(
                        Collector.of(
                                () -> new int[]{0},          //Supplier
                                (acc, num) -> acc[0] += num, // accumulator
                                (left, right) -> {           // Combiner (for parallel streams)
                                    left[0] += right[0];
                                    return left;
                                },
                                acc -> acc[0]                //finisher
                        )
                );
        assertThat(sum).isEqualTo(15);
    }

    @Test
    @DisplayName("Using Collector.of: Suppose you have a list of strings, and you want to count the number of distinct words")
    public void test_3(){
        List<String> words = List.of("apple", "orange", "banana", "apple", "grape", "banana");

        Integer sum = words.stream()
                .collect(
                        Collector.of(
                                HashSet::new,           //Supplier
                                HashSet::add,           // accumulator
                                (set1, set2) -> {       // Combiner (for parallel streams)
                                    set1.addAll(set2);
                                    return set1;
                                },
                                HashSet::size                //finisher
                        )
                );
        assertThat(sum).isEqualTo(4);
    }

    @Test
    @DisplayName("Using Collector.of: Suppose you have a list of strings, and you want to group them by their lengths")
    public void test_4(){
        List<String> words = List.of("apple", "orange", "banana", "grape", "kiwi");

        TreeMap<Integer, List<String>> wordsGroupedByLength = words.stream()
                .collect(
                        Collector.of(
                                TreeMap::new,           //Supplier
                                (map, word) -> {        // accumulator
                                    map.computeIfAbsent(word.length(), (k) -> new ArrayList<>()).add(word);
                                },
                                (map1, map2) -> {                  // Combiner (for parallel streams)
                                    map2.forEach((key, value) -> map1.merge(key, value, (list1, list2) -> {
                                        list1.addAll(list2);
                                        return list1;
                                    }));
                                    return map1;
                                },
                                Function.identity()              //finisher
                        )
                );
        assertThat(wordsGroupedByLength).containsAllEntriesOf(Map.ofEntries(
                Map.entry(4, List.of("kiwi")),
                Map.entry(5, List.of("apple", "grape")),
                Map.entry(6, List.of("orange", "banana"))
        ));
    }

    @Test
    @DisplayName("Using Collector.of: avg of list of numbers")
    public void test_5(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        Integer sum = numbers.parallelStream()
                .collect(
                        Collector.of(
                                () -> new int[]{0,0},          // Supplier
                                (acc, num) -> {                // Accumulator
                                    acc[0] += num;
                                    acc[1]++;
                                },
                                (left, right) -> {           // Combiner (for parallel streams)
                                    left[0] += right[0];
                                    left[1] += right[1];
                                    return left;
                                },
                                acc -> acc[0]/acc[1]                //finisher
                        )
                );
        assertThat(sum).isEqualTo(8);
    }

    @Test
    @DisplayName("Using Collector.of: joining of list of string")
    public void test_6(){
        List<String> numbers = List.of("one", "two", "three", "four", "five");

        String sum = numbers.parallelStream()
                .collect(
                        Collector.of(
                                StringBuilder::new,                      //Supplier
                                (sb, str) -> sb.append(str).append(","), // accumulator
                                StringBuilder::append,                   // combiner (for parallel streams)
                                sb -> {
                                    sb.setLength(sb.length()-1);         //finisher
                                    return sb.toString();
                                }
                        )
                );
        assertThat(sum).isEqualTo("one,two,three,four,five");
    }
}
