package com.vishaga.streams;

import com.vishaga.model.Movie;
import com.vishaga.model.Movie2;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayWithMovieStreamTest {

    private static List<Movie> MOVIES;
    private static List<Movie2> MOVIES2;
    private final Movie FAKE = Movie.fake();
    private final Movie2 FAKE2 = Movie2.fake();

    @BeforeAll
    public static void setUp(){
        MOVIES = MockData.movies(50000);
        MOVIES2 = MockData.movies2(50000);
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
        Map<Integer, Integer> moviesByReleaseYear = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue)));
        assertThat(moviesByReleaseYear).containsAllEntriesOf(
                Map.ofEntries(
                        entry(1931,2),
                        entry(1933,1),
                        entry(1972,48),
                        entry(2015,171),
                        entry(2016,175),
                        entry(2017,212),
                        entry(2020,139),
                        entry(2021,66)
                ));
    }

    @Test
    @DisplayName("Actor whose most numbers of movies released per year")
    public void actorListByYear(){
        Map<Integer, Map.Entry<Long,Set<String>>> maximumMoviesOfActorsPerReleaseYear = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.flatMapping(
                                        movie -> movie.actors().stream(),
                                        Collectors.collectingAndThen(
                                                Collectors.groupingBy(
                                                        Function.identity(),
                                                        Collectors.counting()),
                                                (Map<String, Long> map) -> map.entrySet().stream()
                                                        .collect(
                                                                Collectors.collectingAndThen(
                                                                        Collectors.groupingBy(
                                                                                Map.Entry::getValue,
                                                                                TreeMap::new,
                                                                                Collectors.mapping(
                                                                                        Map.Entry::getKey,
                                                                                        Collectors.toSet()
                                                                                )),
                                                                        TreeMap::lastEntry))))));

        maximumMoviesOfActorsPerReleaseYear.forEach((key, value) -> {
            if(key == 1936){
                assertThat(value.getKey()).isEqualTo(3);
                assertThat(value.getValue()).contains("Devika Rani", "Ashok Kumar");
            }else if(key == 2016){
                assertThat(value.getKey()).isEqualTo(6);
                assertThat(value.getValue()).contains("Jimmy Sheirgill");
            }else if(key == 2018){
                assertThat(value.getKey()).isEqualTo(5);
                assertThat(value.getValue()).contains("Taapsee Pannu", "Manoj Bajpayee");
            }else if(key == 2019){
                assertThat(value.getKey()).isEqualTo(5);
                assertThat(value.getValue()).contains("Bhumi Pednekar");
            }else if(key == 2021){
                assertThat(value.getKey()).isEqualTo(2);
                assertThat(value.getValue()).contains("Parineeti Chopra", "Rajkummar Rao", "Vijay Mahar", "Arjun Kapoor");
            }
        });
    }

    @Test
    @DisplayName("All Actors by Movie released year")
    public void allActorsByReleaseYear(){
        Map<Integer, Set<String>> allActorsByReleaseYear = MOVIES.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::releaseYear,
                                Collectors.flatMapping(
                                        movie -> movie.actors().stream(),
                                        Collectors.toSet())));

        allActorsByReleaseYear.forEach((key, value) -> {
            if(key == 1936){
                assertThat(value).contains("Devika Rani", "Ashok Kumar");
            }else if(key == 2016){
                assertThat(value).contains("Jimmy Sheirgill");
            }else if(key == 2018){
                assertThat(value).contains("Taapsee Pannu", "Manoj Bajpayee");
            }else if(key == 2019){
                assertThat(value).contains("Bhumi Pednekar");
            }else if(key == 2021){
                assertThat(value).contains("Parineeti Chopra", "Rajkummar Rao", "Vijay Mahar", "Arjun Kapoor");
            }
        });
    }

    @Test
    @DisplayName("Movie Name by maximum number of actors in any given year")
    public void movieByMaxActorsByReleaseYear(){
        record MetaData(String movieName, int numberOfActors){};

        Map<Integer, MetaData> movieByReleaseYear = MOVIES2.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie2::releaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparing(movie -> movie.actors().size())),
                                        optMovie -> {
                                            Movie2 movie2 = optMovie.orElse(FAKE2);
                                            return new MetaData(movie2.name(), movie2.actors().size());
                                        })));

        movieByReleaseYear.forEach( (key, value)  -> {
            if(key == 1906){
                assertThat(value.movieName).isEqualTo("The Story of the Kelly Gang");
                assertThat(value.numberOfActors).isEqualTo(13);
            }
        });
    }

    @Test
    @DisplayName("Movie Name by maximum and minimum number of actors in any given year")
    public void movieByMaxAndMinActorsByReleaseYear(){
        Map<Integer, List<String>> minMaxMovieByReleaseYear = MOVIES.stream()
                .collect(Collectors.groupingBy(
                        Movie::releaseYear,
                        Collectors.teeing(
                            Collectors.maxBy(Comparator.comparing((Movie movie) -> movie.actors().size())),
                            Collectors.minBy(Comparator.comparing((Movie movie) -> movie.actors().size())),
                            (m1, m2) -> List.of(m1.orElse(FAKE).name(), m2.orElse(FAKE).name())
                        )));

        assertThat(minMaxMovieByReleaseYear).containsAllEntriesOf(
                Map.ofEntries(
                        entry(1931, List.of("Draupadi","Draupadi")),
                        entry(1932, List.of("Indrasabha","Indrasabha")),
                        entry(1967, List.of("Aamne - Saamne","Aamne - Saamne")),
                        entry(1974, List.of("27 Down","27 Down")),
                        entry(2017, List.of("127 B","A Suitable Girl")),
                        entry(2018, List.of("102 Not Out","Hanuman vs. Mahiravana")),
                        entry(2020, List.of("376 D","Musings of an Uninspired Artist")),
                        entry(2021, List.of("12 O'Clock","Cheer Haran"))
                ));
    }
}
