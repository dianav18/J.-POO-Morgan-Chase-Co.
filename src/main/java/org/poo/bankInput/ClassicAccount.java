package org.poo.bankInput;

/**
 * The type Classic account.
 */
public class ClassicAccount extends Account {
    /**
     * Instantiates a new Classic account.
     *
     * @param iban     the iban
     * @param currency the currency
     */
    public ClassicAccount(final String iban, final String currency) {
        super(iban, currency, "classic");
    }
}
