package com.vishaga.streams;

import com.vishaga.model.Movie2;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithMapMultiTest {

    private static List<Movie2> MOVIES;

    @BeforeAll
    public static void setUp(){
        MOVIES = MockData.movies2(10000);
    }

    @Test
    @DisplayName("mapMulti() example")
    public void mapMultiExample_1(){
        List<List<Integer>> lists = List.of(
                List.of(10,20,21,23,35),
                List.of(30,40),
                List.<Integer>of()
        );

        var numbers = lists.stream()
                .mapMulti(Iterable::forEach)
                .toList();

        assertThat(numbers)
                .hasSize(7)
                .containsOnly(10, 20, 21, 23, 35, 30, 40);
    }

    @Test
    @DisplayName("mapMulti() example")
    public void mapMultiExample_2(){
        var actors = MOVIES.stream()
                .filter(movie2 -> movie2.releaseYear() == 2018)
                .limit(1)
                .mapMulti(((movie2, consumer) -> movie2.actors().forEach(consumer)))
                .toList();

        assertThat(actors.size()).isEqualTo(15);

        List<List<Integer>> lists = List.of(
                List.of(10,20,21,23,35),
                List.of(30,40),
                List.<Integer>of()
        );

        var numbers = lists.stream()
                .mapMulti((list, consumer) -> list.forEach(consumer))
                .toList();

        assertThat(numbers)
                .hasSize(7)
                .containsOnly(10, 20, 21, 23, 35, 30, 40);
    }
}
