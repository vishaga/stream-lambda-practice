package com.vishaga.streams;

import com.vishaga.model.Car;
import com.vishaga.model.Country;
import com.vishaga.model.Region;
import com.vishaga.model.USUniversity;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithGroupingByTest {

    private static List<Car> CARS;
    private static List<USUniversity> US_UNIVERSITIES;
    private static List<Country> COUNTRIES;
    private static final Car FAKE = Car.fake();

    @BeforeAll
    public static void setUp(){
        CARS = MockData.cars();
        US_UNIVERSITIES = MockData.USUniversity();
        COUNTRIES = MockData.countries();
    }

    @Test
    @DisplayName("Counting")
    public void countTest(){
        assertThat((long) CARS.size()).isEqualTo(1000);
        assertThat((long) US_UNIVERSITIES.size()).isEqualTo(231);
        assertThat((long) COUNTRIES.size()).isEqualTo(249);
    }

    @Test
    @DisplayName("Countries by region")
    public void groupingBy_1(){
        Map<Region, List<Country>> countriesByRegion = COUNTRIES.stream()
                .collect(Collectors.groupingBy(Country::region));

        Set<Region> regions = countriesByRegion.keySet();
        assertThat(regions.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Countries by region, sort regions")
    public void groupingBy_2(){
        TreeMap<String, List<Country>> countriesByRegion = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                country -> country.region().name(),
                                TreeMap::new,
                                Collectors.toList()));
        Set<String> regions = countriesByRegion.keySet();

        assertThat(regions.size()).isEqualTo(6);
        assertThat(regions).containsExactly("AFRICA", "AMERICAS", "ASIA", "EUROPE", "NA", "OCEANIA");
    }

    @Test
    @DisplayName("Number of Countries by region.")
    public void groupingBy_3(){
        Map<String, Long> numberOfCountriesBySubRegion = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Country::subRegion,
                                Collectors.counting()));

        assertThat(numberOfCountriesBySubRegion.size()).isEqualTo(18);

        assertThat(numberOfCountriesBySubRegion).containsExactly(
                entry("Northern Africa", 7L),
                entry("Northern Europe", 16L),
                entry("Eastern Europe", 10L),
                entry("Australia and New Zealand", 6L),
                entry("Southern Asia", 9L),
                entry("Central Asia", 5L),
                entry("Sub-Saharan Africa", 53L),
                entry("Western Europe", 9L),
                entry("Northern America", 5L),
                entry("NA", 1L),
                entry("Western Asia", 18L),
                entry("Polynesia", 10L),
                entry("Melanesia", 5L),
                entry("South-eastern Asia", 11L),
                entry("Latin America and the Caribbean", 52L),
                entry("Southern Europe", 16L),
                entry("Micronesia", 8L),
                entry("Eastern Asia", 8L)
        );
    }

    @Test
    @DisplayName("Northern American Countries in sorted order")
    public void groupingBy_4(){
        Map<String, TreeSet<Country>> numberOfCountriesBySubRegion = COUNTRIES.stream()
                .filter(country -> country.subRegion().equals("Northern America"))
                .collect(
                        Collectors.groupingBy(
                                Country::subRegion,
                                Collectors.toCollection(TreeSet::new)));

        assertThat(numberOfCountriesBySubRegion.size()).isEqualTo(1);
        assertThat(numberOfCountriesBySubRegion.get("Northern America").size()).isEqualTo(5);
        assertThat(numberOfCountriesBySubRegion.get("Northern America").stream().map(Country::name).toList())
                .containsExactly("Bermuda","Canada", "Greenland", "Saint Pierre and Miquelon", "United States of America");
    }

    @Test
    @DisplayName("Northern American Countries in sorted order, using mapping() to map Country object to name")
    public void groupingBy_5(){
        Map<String, TreeSet<String>> numberOfCountriesBySubRegion = COUNTRIES.stream()
                .filter(country -> country.subRegion().equals("Northern America"))
                .collect(
                        Collectors.groupingBy(
                                Country::subRegion,
                                Collectors.mapping(
                                        Country::name,
                                        Collectors.toCollection(TreeSet::new))));

        assertThat(numberOfCountriesBySubRegion.size()).isEqualTo(1);
        assertThat(numberOfCountriesBySubRegion.get("Northern America").size()).isEqualTo(5);
        assertThat(numberOfCountriesBySubRegion.get("Northern America"))
                .containsExactly("Bermuda","Canada", "Greenland", "Saint Pierre and Miquelon", "United States of America");
    }

    @Test
    @DisplayName("Countries by region (filtering specific Region only)")
    public void groupingBy_6(){
        Map<Region, List<Country>> numberOfCountriesBySubRegion = COUNTRIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Country::region,
                                Collectors.filtering(
                                        country -> country.region().equals(Region.NA),
                                        Collectors.toList())));

        assertThat(numberOfCountriesBySubRegion.size()).isEqualTo(6);
        assertThat(numberOfCountriesBySubRegion.get(Region.EUROPE)).isEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.OCEANIA)).isEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.AMERICAS)).isEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.ASIA)).isEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.AFRICA)).isEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.NA)).isNotEmpty();
        assertThat(numberOfCountriesBySubRegion.get(Region.NA).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("State wise total college fees")
    public void groupingBy_7(){
        Map<String, Double> numberOfCountriesBySubRegion = US_UNIVERSITIES.stream()
                        .collect(
                                Collectors.groupingBy(
                                        USUniversity::state,
                                        Collectors.summingDouble(USUniversity::fee)));

        assertThat(numberOfCountriesBySubRegion).containsAllEntriesOf(
                Map.ofEntries(
                        entry("HI",33764.0d),
                        entry("DE",31420.0d),
                        entry("MA",610730.0d),
                        entry("FL",208241.0d),
                        entry("WA",99404.0d)
                )
        );
    }

    @Test
    @DisplayName("Costliest college in TX state")
    public void groupingBy_8(){
        Map<String, Optional<USUniversity>> collegeByMaxFeePerSate = US_UNIVERSITIES.stream()
                .filter(usUniversity -> usUniversity.state().equals("TX"))
                .collect(
                        Collectors.groupingBy(
                                USUniversity::state,
                                Collectors.maxBy(Comparator.comparing(USUniversity::fee))));

        collegeByMaxFeePerSate.forEach((key, value) -> {
            if(key.equals("TX")){
                assertThat(value.get().name()).isEqualTo("Southern Methodist University");
            }
        });
    }
}
