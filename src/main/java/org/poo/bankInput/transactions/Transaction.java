package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Transaction.
 */
@Getter
public abstract class Transaction {
    private final int timestamp;
    private final String description;

    /**
     * Instantiates a new Transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public Transaction(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    /**
     * Accept.
     *
     * @param visitor the visitor
     */
    public abstract void accept(TransactionVisitor visitor);
}

