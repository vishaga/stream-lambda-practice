package com.vishaga.streams;

import com.vishaga.model.*;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithCollectorsToMapTest {

    static List<City> CITIES;

    @BeforeAll
    public static void setUp(){
        CITIES = DataLoaderUtils.loadCities();
    }

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper)")
    public void test_1(){
        Map<Integer, Integer> populationByRank = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                City::rank,
                                City::population
                        )
                );
        assertThat(populationByRank).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry(1,24153000),
                        Map.entry(2,18590000),
                        Map.entry(3,18000000),
                        Map.entry(4,14657000),
                        Map.entry(297,595000),
                        Map.entry(298,578000),
                        Map.entry(299,433000),
                        Map.entry(300,361000)
                )
        );
    }

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper, MergeFunction)")
    public void test_2(){
        Map<String, Integer> populationByCountry = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                City::country,
                                City::population,
                                //(p1, p2) -> p1 + p2
                                Integer::sum
                        )
                );

        assertThat(populationByCountry).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("Cameroon",4982000),
                        Map.entry("Angola",2108000),
                        Map.entry("Cambodia",1502000),
                        Map.entry("India",96767000),
                        Map.entry("Germany",6908000),
                        Map.entry("China",253364300)

                )
        );
    }
}
