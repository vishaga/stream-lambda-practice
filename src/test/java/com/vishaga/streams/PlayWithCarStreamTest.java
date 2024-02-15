package com.vishaga.streams;

import com.vishaga.model.Car;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

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

    @Test
    @DisplayName("Find costliest Car")
    public void reverseSortCarsByModelThenColor(){

        Comparator<Car> carComparator = Comparator.comparing(Car::model)
                .reversed()
                .thenComparing(Car::color);
        List<Integer> carIds = CARS.stream()
                .filter(car -> car.brand().equals("Jeep"))
                .sorted(carComparator).map(Car::id).toList();

        assertThat(carIds).containsExactly(355, 71, 779, 213, 73, 574, 422, 295, 90, 371, 710, 674);
    }

    @Test
    @DisplayName("Find costliest Car in each Brand")
    public void costliestCarInEachBrand(){

        Map<String, Integer> costlierCarByBrand = CARS.stream()
                .collect(
                        Collectors.groupingBy(
                                Car::brand,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(
                                                Comparator.comparing(
                                                        Car::price)),
                                        optionalCar -> optionalCar.orElse(FAKE).id())
                        )
                );

        assertThat(costlierCarByBrand).containsAllEntriesOf(
                Map.ofEntries(
                        entry("Jeep",73),
                        entry("Lexus",561),
                        entry("Subaru",771),
                        entry("Fillmore",694),
                        entry("Eagle",809)
                )
        );
    }

}