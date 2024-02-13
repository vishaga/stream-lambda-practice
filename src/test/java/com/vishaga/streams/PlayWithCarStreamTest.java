package com.vishaga.streams;

import com.vishaga.model.Car;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithCarStreamTest {

    private static List<Car> CARS;
    private static final Car FAKE = Car.fake();

    @BeforeAll
    public static void setUp(){
        CARS = MockData.cars();
    }

    @Test
    @DisplayName("Count Cars")
    public void countTest(){
        assertThat((long) CARS.size()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Find costliest Car")
    public void mostCostliestCar(){
        Car costliesrCar = CARS.stream().max(Comparator.comparing(Car::price)).orElse(FAKE);
        assertThat(costliesrCar.id()).isEqualTo(291);
        assertThat(costliesrCar.brand()).isEqualTo("Mitsubishi");
        assertThat(costliesrCar.model()).isEqualTo("L300");
        assertThat(costliesrCar.price()).isEqualTo(99932.82);
    }

}
