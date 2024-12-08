package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavingsAccount extends Account {
    private double interestRate;
    public SavingsAccount(final String iban, final String currency, final double interestRate) {
        super(iban, currency, "savings");
        this.interestRate = interestRate;
    }
}
