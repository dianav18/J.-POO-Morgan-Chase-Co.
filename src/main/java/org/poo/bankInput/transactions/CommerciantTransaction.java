package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class CommerciantTransaction extends Transaction {
    private final String commerciant;
    private final double amount;
    private final int timestamp;
    public CommerciantTransaction(final String commerciant, final double amount, final int timestamp) {
        super(timestamp, "Card payment");
        this.commerciant = commerciant;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
