package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavingsAccount extends Account {
    private double interestRate = 0;
    public SavingsAccount(final String iban, final String currency, final double interestRate) {
        super(iban, currency, "savings");
        this.interestRate = interestRate;
    }

    public void addInterest(final double interestRate) {
        this.interestRate += interestRate;
    }

    public void changeInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }
}
