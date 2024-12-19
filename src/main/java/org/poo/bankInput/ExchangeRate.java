package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Exchange rate.
 */
@Getter
@Setter
public class ExchangeRate {
    private String from;
    private String to;
    private double rate;
    private int timestamp;

    /**
     * Instantiates a new Exchange rate.
     *
     * @param from      the from
     * @param to        the to
     * @param rate      the rate
     * @param timestamp the timestamp
     */
    public ExchangeRate(final String from, final String to,
                        final double rate, final int timestamp) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.timestamp = timestamp;
    }
}
