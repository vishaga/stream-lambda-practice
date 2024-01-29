package com.vishaga.streams;

import com.vishaga.model.*;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
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
                        ));

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

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper, MergeFunction, Supplier)")
    public void test_3(){
        TreeMap<String, Integer> countryToPopulationSortByName = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                City::country,
                                City::population,
                                Integer::sum,
                                TreeMap::new
                        ));

        assertThat(countryToPopulationSortByName.getClass()).isEqualTo(TreeMap.class);

        assertThat(countryToPopulationSortByName).containsAllEntriesOf(
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

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper, MergeFunction, Supplier)")
    public void test_4(){
        TreeMap<String, Integer> numberOfCountriesByFirstLetter = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                city -> city.country().substring(0,1),
                                city -> 1,
                                Integer::sum,
                                TreeMap::new
                        ));

        assertThat(numberOfCountriesByFirstLetter.getClass()).isEqualTo(TreeMap.class);

        assertThat(numberOfCountriesByFirstLetter).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("A",14),
                        Map.entry("B",21),
                        Map.entry("C",60),
                        Map.entry("U",18),
                        Map.entry("V",7),
                        Map.entry("Y",1),
                        Map.entry("Z",2)

                )
        );
    }

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper, MergeFunction, Supplier)")
    public void test_5(){
        TreeMap<String, String> countriesByFirstLetter = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                city -> city.country().substring(0,1),
                                City::country,
                                (str1, str2) -> str1.concat(",").concat(str2),
                                TreeMap::new
                        ));

        assertThat(countriesByFirstLetter.getClass()).isEqualTo(TreeMap.class);

        assertThat(countriesByFirstLetter).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("A","Australia,Algeria,Azerbaijan,Australia,Argentina,Argentina,Afghanistan,Angola,Australia,Australia,Argentina,Australia,Austria,Armenia"),
                        Map.entry("U","UK,USA,USA,Ukraine,Ukraine,USA,Ukraine,Ukraine,UK,USA,Uruguay,USA,Ukraine,USA,USA,USA,USA,Uzbekistan"),
                        Map.entry("V","Venezuela,Venezuela,Vietnam,Vietnam,Vietnam,Venezuela,Venezuela"),
                        Map.entry("Y","Yemen"),
                        Map.entry("Z","Zimbabwe,Zambia")

                )
        );
    }

    @Test
    @DisplayName("Using toMap(KeyMapper, ValueMapper, MergeFunction, Supplier)")
    public void test_6(){
        TreeMap<String, Set<String>> countriesByFirstLetter = CITIES.stream()
                .collect(
                        Collectors.toMap(
                                city -> city.country().substring(0,1),
                                city -> Set.of(city.country()),
                                (Set<String> set1, Set<String> set2) -> {
                                    Set<String> set = new HashSet<>(set1);
                                    set.addAll(set2);
                                    return set;
                                },
                                TreeMap::new
                        ));

        assertThat(countriesByFirstLetter).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry("A",Set.of("Armenia", "Azerbaijan", "Argentina", "Afghanistan", "Austria", "Angola", "Algeria", "Australia")),
                        Map.entry("U",Set.of("USA", "Uruguay", "UK", "Ukraine", "Uzbekistan")),
                        Map.entry("V",Set.of("Venezuela", "Vietnam")),
                        Map.entry("Y",Set.of("Yemen")),
                        Map.entry("Z",Set.of("Zambia","Zimbabwe"))

                )
        );
    }
}
