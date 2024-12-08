package org.poo.bankInput;

public class ClassicAccount extends Account {
    public ClassicAccount(final String iban, final String currency) {
        super(iban, currency, "classic");
    }
}
