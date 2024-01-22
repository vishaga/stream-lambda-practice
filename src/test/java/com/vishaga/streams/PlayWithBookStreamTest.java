package com.vishaga.streams;

import com.vishaga.model.Book;
import com.vishaga.model.Country;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithBookStreamTest {

    private static List<Book> BOOKS;

    @BeforeAll
    public static void setUp(){
        BOOKS = DataLoaderUtils.loadBooks();
    }

    @Test
    @DisplayName("Count Books")
    public void countTest(){
        assertThat((long) BOOKS.size()).isEqualTo(1069);
    }
}
