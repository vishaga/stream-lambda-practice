package com.vishaga.streams;

import com.vishaga.model.Car;
import com.vishaga.model.Country;
import com.vishaga.model.Region;
import com.vishaga.model.USUniversity;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
                .collect(
                        Collectors.groupingBy(
                                Country::region));
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

}
