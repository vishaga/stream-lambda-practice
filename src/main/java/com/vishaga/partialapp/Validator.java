package com.vishaga.partialapp;

import com.vishaga.java.util.Predicate;

import java.util.function.Supplier;

public interface Validator<T> {

    class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    Supplier<T> validate(T t);

    static <T> Validator<T> firstValidate(Predicate<T> predicate, String message){
        return t -> predicate.test(t) ?
                () -> {
                    ValidationException ex = new ValidationException("Invalid Object");
                    ex.addSuppressed(new IllegalArgumentException(message));
                    throw ex;
                }:
                () -> t;
    }

//    default Validator<T> thenValidate(Predicate<T> predicate, String message){
//        return t ->
//                () -> predicate.test(t)
//    }
}
