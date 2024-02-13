package com.vishaga.partialapp;

import com.vishaga.utils.MockData;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface CurrentConverter {

    double convert(double amount);

    interface BiFunction{
        Double convert(double amount, String toCurrency);

        default CurrentConverter to(String toCurrency){
            return amount -> convert(amount, toCurrency);
        }
    }

    interface TriFunction{
        Double convert(double amount, String fromCurrency, String toCurrency);

        default BiFunction from(String fromCurrency){
            return (amount,toCurrency) -> convert(amount,fromCurrency,toCurrency);
        }
    }

    static TriFunction of(LocalDate date){
        return (amount, fromCurrency, toCurrency) -> {
            Map<LocalDate, Map<String, Double>> currencyConversion = MockData.loadCurrencyConversion();
            Map<String, Double> dateCurrentMap = currencyConversion.getOrDefault(date, new HashMap<>());
            return amount *
                    (dateCurrentMap.getOrDefault(toCurrency, 1d)/
                            dateCurrentMap.getOrDefault(fromCurrency, 1d));
        };
    }
}
