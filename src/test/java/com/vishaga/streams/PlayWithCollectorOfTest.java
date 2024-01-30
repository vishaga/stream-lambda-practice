package com.vishaga.streams;

import com.vishaga.model.City;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
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
}
