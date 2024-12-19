package org.poo.handlers;

import org.poo.bankInput.ExchangeRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Currency converter.
 */
public class CurrencyConverter {
    private final Map<String, Double> conversionRates;

    /**
     * Instantiates a new Currency converter.
     *
     * @param exchangeRates the exchange rates
     */
    public CurrencyConverter(final List<ExchangeRate> exchangeRates) {
        this.conversionRates = new HashMap<>();

        for (final ExchangeRate rate : exchangeRates) {
            addRate(rate.getFrom(), rate.getTo(), rate.getRate());
        }
    }

    private void addRate(final String from, final String to, final double rate) {
        final String key = generateKey(from, to);
        conversionRates.put(key, rate);

        final String reverseKey = generateKey(to, from);
        if (!conversionRates.containsKey(reverseKey)) {
            conversionRates.put(reverseKey, 1 / rate);
        }
    }

    /**
     * Convert double.
     *
     * @param amount the amount
     * @param from   the from
     * @param to     the to
     * @return the double
     */
    public double convert(final double amount, final String from, final String to) {
        if (from.equals(to)) {
            return amount;
        }

        final String key = generateKey(from, to);
        final Double directRate = conversionRates.get(key);

        if (directRate != null) {
            return amount * directRate;
        }

        for (final String intermediate : getAllCurrencies()) {
            final String firstKey = generateKey(from, intermediate);
            final String secondKey = generateKey(intermediate, to);

            final Double firstRate = conversionRates.get(firstKey);
            final Double secondRate = conversionRates.get(secondKey);

            if (firstRate != null && secondRate != null) {
                final double indirectRate = firstRate * secondRate;
                addRate(from, to, indirectRate);
                return amount * indirectRate;
            }
        }

        return -1;
    }

    private String generateKey(final String from, final String to) {
        return from + "->" + to;
    }


    private List<String> getAllCurrencies() {
        return conversionRates.keySet().stream()
                .flatMap(key -> List.of(key.split("->")).stream())
                .distinct()
                .toList();
    }
}
