package com.vishaga.streams;

import com.vishaga.model.Movie;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayWithMovieStreamTest {

    private static List<Movie> MOVIES;

    @BeforeAll
    public static void setUp(){
        MOVIES = DataLoaderUtils.loadMovie(100000);
    }

    @Test
    @DisplayName("Count number of entries in collection")
    public void countTest(){
        assertThat((long) MOVIES.size()).isEqualTo(5546);
    }

    @Test
    @DisplayName("Release Year count")
    public void releaseYearCount(){
        Map<Integer, List<Movie>> moviesByReleaseYear = MOVIES.stream()
                .collect(Collectors.groupingBy(Movie::releaseYear));

        assertThat(moviesByReleaseYear.entrySet().size()).isEqualTo(91);
    }

    @Test
    @DisplayName("Number of Movies released by year")
    public void releasedMoviesByYear(){
        Map<Integer, Long> moviesByYear1 = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.counting()));
        moviesByYear1.forEach((key, value) -> assertThat(value).isGreaterThan(0));

        Map<Integer, Integer> moviesByYear2 = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue
                                )));
        moviesByYear2.forEach((key, value) -> assertThat(value).isGreaterThan(0));
    }

    @Test
    @DisplayName("All Actors by Movie released year")
    public void actorListByYear(){

        Map<Integer, Set<String>> collect = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.flatMapping(
                                        movie -> movie.actors().stream(),
                                        Collectors.toSet()
                                )));
        System.out.println("collect = " + collect);

        Map<Integer, Set<List<String>>> collect1 = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.mapping(
                                        Movie::actors,
                                        Collectors.toSet()
                                )));
    }

    @Test
    @DisplayName("All Actors by Movie released year")
    public void minMaxCountOfMoviesReleasedByYear(){

        Map<Integer, Integer> collect = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue
                                )));
        MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue
                                )));
        //System.out.println("collect = " + collect);

    }

    @Test
    @DisplayName("Movie Name by maximum number of actors in any given year")
    public void movieByMaxActorsByReleaseYear(){
        Map<Integer, String> movieByReleaseYear = MOVIES.stream()
                .collect(Collectors.groupingBy(
                        Movie::releaseYear,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(movie -> movie.actors().size())),
                                optMovie -> optMovie.map(Movie::name).orElse("NA")
                        ))
                );
        movieByReleaseYear.forEach( (key, value)  -> assertThat(value.length()).isGreaterThan(2));
    }

    @Test
    @DisplayName("Movie Name by maximum number of actors in any given year")
    public void movieByMaxAndMinActorsByReleaseYear(){

        record MinMaxMovie(Movie m1, Movie m2){};

        Movie fake = Movie.fake();

        Map<Integer, MinMaxMovie> minMaxMovieByReleaseYear = MOVIES.stream()
                .collect(Collectors.groupingBy(
                        Movie::releaseYear,
                        Collectors.collectingAndThen(
                                Collectors.teeing(
                                        Collectors.maxBy(Comparator.comparing((Movie movie) -> movie.actors().size())),
                                        Collectors.minBy(Comparator.comparing((Movie movie) -> movie.actors().size())),
                                        (m1, m2) -> new MinMaxMovie(m1.orElse(fake), m2.orElse(fake))
                                ),
                                m -> m
                        ))
                );

        minMaxMovieByReleaseYear.forEach( (key, value)  -> {
            assertThat(value.m1).isNotEqualTo(fake);
            assertThat(value.m2).isNotEqualTo(fake);
        });

        minMaxMovieByReleaseYear.forEach( (key, value)  -> {
            System.out.println(key + ": " + value.m1.name() + ": " + value.m2.name());
            //assertThat(value.m2).isNotEqualTo(fake);
        });
    }
}
