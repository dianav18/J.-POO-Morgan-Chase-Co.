package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class CardPaymentTransaction extends Transaction{
    private final double amount;
    private final String commerciant;
    private final String description;
    private final int timestamp;

    public CardPaymentTransaction(final int timestamp, final String description, final double amount, final String commerciant, final int timestamp1) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
        this.description = description;
        this.timestamp = timestamp1;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
