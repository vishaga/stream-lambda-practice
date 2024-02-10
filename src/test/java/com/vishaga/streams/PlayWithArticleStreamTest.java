package com.vishaga.streams;

import com.vishaga.model.Article;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithArticleStreamTest {

    static List<Article> ARTICLES;
    static final Article FAKE = Article.fake();

    @BeforeAll
    public static void setUp(){
        ARTICLES = DataLoaderUtils.loadArticles();
    }

    @Test
    @DisplayName("Count of Articles")
    public void practiceTest_1(){
        assertThat(ARTICLES.stream().count()).isEqualTo(123336);
        assertThat(ARTICLES.size()).isEqualTo(123336);
        assertThat(ARTICLES.stream().collect(Collectors.counting())).isEqualTo(123336);
    }

    @Test
    @DisplayName("Min Inception Year of Articles")
    public void practiceTest_2(){

        // sorted intermediate operation with limit to gain the min value.
        List<Article> articleWithMinInceptionYear1 = ARTICLES.stream()
                .sorted(Comparator.comparing(Article::inceptionYear))
                .limit(1)
                .toList();

        assertThat(articleWithMinInceptionYear1.isEmpty()).isFalse();
        assertThat(articleWithMinInceptionYear1.get(0).inceptionYear()).isEqualTo(1935);

        // with min() terminal operation.
        Optional<Article> articleWithMinInceptionYear2 = ARTICLES.stream()
                .min(Comparator.comparing(Article::inceptionYear));

        assertThat(articleWithMinInceptionYear2.isEmpty()).isFalse();
        assertThat(articleWithMinInceptionYear1.get(0).inceptionYear()).isEqualTo(1935);
    }

    @Test
    @DisplayName("Min and Max Titles by Inception Year of Articles")
    public void practiceTest_3(){
        List<String> minMaxArticle = ARTICLES.stream()
                .collect(
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparing(Article::inceptionYear)),
                                Collectors.maxBy(Comparator.comparing(Article::inceptionYear)),
                                (min, max) -> List.of(min.orElse(FAKE).title(), max.orElse(FAKE).title())));

        assertThat(minMaxArticle)
                .contains("A Combinatorial Problem in Geometry","Barcodes:  The Persistent Topology of Data");
    }

    @Test
    @DisplayName("summaryStatistics by Inception Year of Articles")
    public void practiceTest_4(){
        IntSummaryStatistics summaryStatistics = ARTICLES.stream()
                .collect(
                        Collectors.summarizingInt(Article::inceptionYear));

        assertThat(summaryStatistics.getMin()).isEqualTo(1935);
        assertThat(summaryStatistics.getMax()).isEqualTo(2008);
        assertThat(summaryStatistics.getCount()).isEqualTo(123336);
    }

    @Test
    @DisplayName("All Titles (comma separated) of 1960 Inception Year")
    public void practiceTest_5(){
        String titlesOf1960Articles = ARTICLES.stream()
                .filter(article -> article.inceptionYear() == 1960)
                .map(Article::title)
                .collect(Collectors.joining(","));

        assertThat(titlesOf1960Articles)
                .isEqualTo(
                        "The Pernicious Influence of Mathematics on Science,Degree of Difficulty of Computing a Function and a Partial Ordering of Recursive Sets");
    }

    @Test
    @DisplayName("Number of Articles by Inception Year")
    public void practiceTest_6(){
        Map<Integer,Long> numberOfArticlePerYear = ARTICLES.stream()
                .collect(
                        Collectors.groupingBy(
                                Article::inceptionYear,
                                Collectors.counting()));

        assertThat(numberOfArticlePerYear).containsAllEntriesOf(
                Map.ofEntries(
                        Map.entry(1935, 1L),
                        Map.entry(1960, 2L),
                        Map.entry(2000, 6342L),
                        Map.entry(2001, 5981L),
                        Map.entry(2002, 6399L),
                        Map.entry(2003, 7234L),
                        Map.entry(2004, 7682L),
                        Map.entry(2005, 6644L),
                        Map.entry(2006, 3887L),
                        Map.entry(2007, 3135L),
                        Map.entry(2008, 75L)
                )
        );
    }

    @Test
    @DisplayName("Inception Year with max Number of Articles Published")
    public void practiceTest_7(){
        List<Integer> listOfInceptionYearsByMaxArticles = ARTICLES.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Article::inceptionYear,
                                        Collectors.counting()),
                                (map) -> map.entrySet().stream() // Stream<Map.Entry<Integer, Long>>
                                            .collect(
                                                    Collectors.collectingAndThen(
                                                            Collectors.groupingBy(
                                                                    Map.Entry::getValue,
                                                                    //() -> new TreeMap<>(Comparator.reverseOrder()),
                                                                    TreeMap::new,
                                                                    Collectors.mapping(Map.Entry::getKey, Collectors.toList())),
                                                            anotherMap -> anotherMap.lastEntry().getValue()))));

       assertThat(listOfInceptionYearsByMaxArticles).contains(2004);
    }

    @Test
    @DisplayName("Author(s) or Co-Author(s) who published maximum number of Books")
    public void practiceTest_8(){
        Map.Entry<Long, List<String>> coAuthorsWithMaximumPublishedBooks =
                ARTICLES.stream()
                .<String>mapMulti((article, consumer) -> article.authors().forEach(consumer))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Function.identity(), Collectors.counting()),
                                (Map<String, Long> map) -> map.entrySet().stream()
                                        .collect(
                                                Collectors.collectingAndThen(
                                                        Collectors.groupingBy(
                                                                Map.Entry::getValue,
                                                                TreeMap::new,
                                                                //() -> new TreeMap<>(Comparator.reverseOrder()),
                                                                Collectors.mapping(
                                                                        Map.Entry::getKey,
                                                                        Collectors.toList())),
                                                        TreeMap::lastEntry
                                                )
                                        )
                        )
                );

       assertThat(coAuthorsWithMaximumPublishedBooks != null).isTrue();
       assertThat(coAuthorsWithMaximumPublishedBooks.getKey()).isEqualTo(1392);
       assertThat(coAuthorsWithMaximumPublishedBooks.getValue()).contains("Chen");
    }
}
