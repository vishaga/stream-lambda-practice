package com.vishaga.streams;

import com.vishaga.model.Population;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithPopulationStreamTest {

    private static List<Population> POPULATION;

    private static final Population FAKE = Population.fake();

    @BeforeAll
    public static void setUp(){
        POPULATION = DataLoaderUtils.loadPopulation();
    }

    @Test
    @DisplayName("Count data count.")
    public void numberData(){
        assertThat((long) POPULATION.size()).isEqualTo(1692);
    }

    @Test
    @DisplayName("Max and Min city by population each country")
    public void maxAndMinPopulationCitiesByCountry(){
        Map<String, List<String>> minMaxCityByPopulation = POPULATION.stream().collect(
                Collectors.groupingBy(
                        Population::country,
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparing(Population::in_2010)),
                                Collectors.maxBy(Comparator.comparing(Population::in_2010)),
                                (min, max) -> List.of(min.orElse(FAKE).city(), max.orElse(FAKE).city())
                        )
                )
        );
        assertThat(minMaxCityByPopulation).containsAllEntriesOf(
                Map.ofEntries(
                    entry("India", List.of("Hosur", "Delhi")),
                    entry("Afghanistan", List.of("Herat", "Kabul")),
                    entry("Albania", List.of("Tiranë (Tirana)", "Tiranë (Tirana)")),
                    entry("Algeria", List.of("Batna", "El Djazaïr  (Algiers)")),
                    entry("Iraq", List.of("Faloojah", "Baghdad")),
                    entry("United States of America", List.of("The Woodlands", "New York-Newark")),
                    entry("France", List.of("Rennes", "Paris"))
                )
        );
    }


    @Test
    @DisplayName("Total population by each country")
    public void totalPopulationByCountry(){
        Map<String, Integer> totalPopulationByCountry = POPULATION.stream().collect(
                Collectors.groupingBy(
                        Population::country,
                        Collectors.mapping(
                                Population::in_2010,
                                Collectors.reducing(0, Integer::sum)
                        )
                )
        );
        assertThat(totalPopulationByCountry).containsAllEntriesOf(
                Map.ofEntries(
                        entry("India", 215030000),
                        entry("Afghanistan", 4399000),
                        entry("Albania", 411000),
                        entry("Algeria", 4977000),
                        entry("Iraq", 14934000),
                        entry("Iran (Islamic Republic of)", 27492000),
                        entry("United States of America", 180761000),
                        entry("France", 22871000)
                )
        );
    }
}
