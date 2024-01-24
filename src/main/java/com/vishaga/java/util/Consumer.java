package com.vishaga.java.util;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);

    default Consumer<T> andThen(Consumer<T> other){
        return (t) -> {
            this.accept(t);
            other.accept(t);
        };
    }
}
