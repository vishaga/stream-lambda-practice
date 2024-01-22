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

        numberOfCountriesBySubRegion.forEach((key, value) -> {
            if(!key.contains("Asia")){
                assertThat(value).isEqualTo(0L);
            }else{
                assertThat(value).isGreaterThan(4L);
            }
        });

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

        onlyAsianSubRegionCountriesInInteger.forEach((key, value) -> {
            if(!key.contains("Asia")){
                assertThat(value).isEqualTo(0);
            }else{
                assertThat(value).isGreaterThan(4);
            }
        });

    }
}
