package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class InsufficientFundsTransaction extends Transaction {
    private final String description;
    private final int timestamp;

    public InsufficientFundsTransaction(final int timestamp, final String description) {
        super(timestamp, description);
        this.description = description;
        this.timestamp = timestamp;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
