package com.vishaga.streams;

import com.vishaga.model.Car;
import com.vishaga.model.Country;
import com.vishaga.model.Region;
import com.vishaga.model.USUniversity;
import com.vishaga.stream.ListCollectors;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomListCollectorTest {

    private static List<Country> COUNTRIES;

    @BeforeAll
    public static void setUp(){
        COUNTRIES = MockData.countries();
    }

    @Test
    @DisplayName("Counting")
    public void countTest(){
        assertThat((long) COUNTRIES.size()).isEqualTo(249);
    }

    @Test
    @DisplayName("Counting")
    public void getRegions(){

        List<String> regions = COUNTRIES.stream()
                .map(Country::region)
                .map(Region::name)
                .distinct()
                .collect(ListCollectors.toList());

        assertThat(regions)
                .hasSize(6)
                .containsExactly("ASIA", "EUROPE", "AFRICA", "OCEANIA", "AMERICAS", "NA");

    }
}
