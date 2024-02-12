package com.vishaga.streams;

import com.vishaga.model.USUniversity;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithUSUniversitiesStreamTest {

    private static List<USUniversity> US_UNIVERSITIES;

    @BeforeAll
    public static void setUp(){
        US_UNIVERSITIES = DataLoaderUtils.loadUSUniversity();
    }

    @Test
    @DisplayName("Count Universities")
    public void countTest(){
        assertThat((long) US_UNIVERSITIES.size()).isEqualTo(231);
    }

    @Test
    @DisplayName("Count Universities by US State")
    public void countUniversitiesByState(){
        Map<String, Long> numberOfUniversitiesByState = US_UNIVERSITIES.stream()
                .collect(
                        Collectors.groupingBy(
                                USUniversity::state,
                                Collectors.counting()));

        assertThat(numberOfUniversitiesByState).containsAllEntriesOf(
                Map.ofEntries(
                        entry("GA",4L),
                        entry("TX",10L),
                        entry("MA",15L),
                        entry("IL",11L),
                        entry("OK",3L),
                        entry("CA",22L)
                )
        );
    }

}
