package com.vishaga.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListCollectors<T> implements Collector<T, List<T>,List<T>> {

    private ListCollectors(){
        //
    }

    public static <T> Collector<T, List<T>,List<T>> toList(){
        return new ListCollectors<>();
    }

    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) ->
                Stream.concat(list1.stream(),list2.stream())
                        .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return Collections::unmodifiableList;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
