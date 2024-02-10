package com.vishaga.streams;

import com.vishaga.model.Country;
import com.vishaga.model.Region;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithCountryStreamTest {

    private static List<Country> COUNTRIES;

    @BeforeAll
    public static void setUp(){
        COUNTRIES = DataLoaderUtils.loadCountries();
    }

    @Test
    @DisplayName("Count Countries")
    public void countTest(){
        assertThat((long) COUNTRIES.size()).isEqualTo(249);
    }

    @Test
    @DisplayName("Number of Countries by Region")
    public void numberOfCountriesByRegion(){
        Map<Region, Long> numberOfCountriesByRegion = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Country::region,
                                Collectors.counting()));

        assertThat(numberOfCountriesByRegion).contains(
                entry(Region.AFRICA, 60L),
                entry(Region.ASIA, 51L),
                entry(Region.AMERICAS, 57L),
                entry(Region.OCEANIA, 29L),
                entry(Region.EUROPE, 51L),
                entry(Region.NA, 1L)
        );
    }

    @Test
    @DisplayName("Number of Asian Countries by Sub Region")
    public void numberOfAsianCountriesBySubRegion(){
        Map<String, Long> numberOfCountriesBySubRegion = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Country::subRegion,
                                Collectors.filtering(
                                        country -> country.subRegion().contains("Asia"),
                                        Collectors.counting())));

        assertThat(numberOfCountriesBySubRegion).containsAllEntriesOf(Map.ofEntries(
                Map.entry("Northern Africa", 0L),
                Map.entry("Northern Europe", 0L),
                Map.entry("Eastern Europe", 0L),
                Map.entry("Australia and New Zealand", 0L),
                Map.entry("Southern Asia", 9L),
                Map.entry("Central Asia", 5L),
                Map.entry("Sub-Saharan Africa", 0L),
                Map.entry("Western Europe", 0L),
                Map.entry("Northern America", 0L),
                Map.entry("NA", 0L),
                Map.entry("Western Asia", 18L),
                Map.entry("South-eastern Asia", 11L),
                Map.entry("Eastern Asia", 8L),
                Map.entry("Micronesia", 0L)
        ));

        Map<String, Integer> onlyAsianSubRegionCountriesInInteger = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Country::subRegion,
                                Collectors.filtering(
                                        country -> country.subRegion().contains("Asia"),
                                        Collectors.collectingAndThen(
                                                Collectors.counting(),
                                                Long::intValue
                                        )
                                )
                        ));

        assertThat(onlyAsianSubRegionCountriesInInteger).containsAllEntriesOf(Map.ofEntries(
                Map.entry("Northern Africa", 0),
                Map.entry("Northern Europe", 0),
                Map.entry("Eastern Europe", 0),
                Map.entry("Australia and New Zealand", 0),
                Map.entry("Southern Asia", 9),
                Map.entry("Central Asia", 5),
                Map.entry("Sub-Saharan Africa", 0),
                Map.entry("Western Europe", 0),
                Map.entry("Northern America", 0),
                Map.entry("NA", 0),
                Map.entry("Western Asia", 18),
                Map.entry("South-eastern Asia", 11),
                Map.entry("Eastern Asia", 8),
                Map.entry("Micronesia", 0)
        ));

    }
}
