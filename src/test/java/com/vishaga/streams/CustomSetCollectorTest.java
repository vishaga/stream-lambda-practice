package com.vishaga.streams;

import com.vishaga.model.Country;
import com.vishaga.model.Region;
import com.vishaga.stream.SetCollectors;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomSetCollectorTest {

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

        Set<String> regions = COUNTRIES.stream()
                .map(Country::region)
                .map(Region::name)
                .collect(SetCollectors.toSet());

        assertThat(regions)
                .hasSize(6)
                .containsOnly("ASIA", "EUROPE", "AFRICA", "OCEANIA", "AMERICAS", "NA");
    }
}
