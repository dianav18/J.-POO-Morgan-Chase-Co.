package org.poo.handlers;

import org.poo.bankInput.ExchangeRate;
import org.poo.fileio.ExchangeInput;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateMapper {
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
