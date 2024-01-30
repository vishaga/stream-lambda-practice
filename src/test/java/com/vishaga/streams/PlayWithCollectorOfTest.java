package com.vishaga.streams;

import com.vishaga.model.City;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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
}
