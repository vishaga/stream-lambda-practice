package com.vishaga.partialapp;

import com.vishaga.java.util.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PartialApplicationTest {

    @Test
    @DisplayName("Additional of 2 number using partial function")
    public void practiceTest_1() {
        Function<Integer, Function<Integer, Integer>> func = x -> y -> x + y;

        Function<Integer, Integer> func2 = func.apply(10);

        assertThat(func2.apply(20)).isEqualTo(30);
        assertThat(func2.apply(30)).isEqualTo(40);
        assertThat(func2.apply(200)).isEqualTo(210);
    }

    @Test
    @DisplayName("Multiplication of 2 number using partial function")
    public void practiceTest_2() {
        Function<Integer, Function<Integer, Integer>> func = x -> {
            // System.out.println("Outer");
            return y -> {
                // System.out.println("Inner");
               return x * y;
            };
        };

        Function<Integer, Integer> func2 = func.apply(10);

        assertThat(func2.apply(20)).isEqualTo(200);
        assertThat(func2.apply(30)).isEqualTo(300);
        assertThat(func2.apply(200)).isEqualTo(2000);
    }

    @Test
    @DisplayName("Multiplication of 2 number using partial function")
    public void practiceTest_3() {
        double convert = CurrentConverter.of(LocalDate.now())
                .from("EUR")
                .to("INR")
                .convert(100);

        assertThat(convert).isEqualTo(8255.699d);
    }
}
