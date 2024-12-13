package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public abstract class Transaction {
    private final int timestamp;
    private final String description;

    public Transaction(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public abstract void accept(TransactionVisitor visitor);
}

