package org.poo.handlers;

import org.poo.bankInput.ExchangeRate;
import org.poo.fileio.ExchangeInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Exchange rate mapper.
 */
public final class ExchangeRateMapper {
    /**
     * Private constructor to prevent instantiation.
     */
    private ExchangeRateMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Map to exchange rates list.
     *
     * @param exchangeInputs the exchange inputs
     * @return the list
     */
    public static List<ExchangeRate> mapToExchangeRates(final ExchangeInput[] exchangeInputs) {
        final List<ExchangeRate> exchangeRates = new ArrayList<>();
        if (exchangeInputs != null) {
            for (final ExchangeInput exchangeInput : exchangeInputs) {
                exchangeRates.add(new ExchangeRate(
                        exchangeInput.getFrom(),
                        exchangeInput.getTo(),
                        exchangeInput.getRate(),
                        exchangeInput.getTimestamp()
                ));
            }
        }
        return exchangeRates;
    }
}
