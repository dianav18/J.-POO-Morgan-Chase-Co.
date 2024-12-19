package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Change interest rate transaction.
 */
@Getter
public final class ChangeInterestRateTransaction extends Transaction {
    private final double interestRate;

    /**
     * Instantiates a new Change interest rate transaction.
     *
     * @param timestamp    the timestamp
     * @param interestRate the interest rate
     * @param description  the description
     */
    public ChangeInterestRateTransaction(final int timestamp, final double interestRate,
                                         final String description) {
        super(timestamp, description);
        //this.accountIBAN = accountIBAN;
        this.interestRate = interestRate;
        //this.description = description;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
