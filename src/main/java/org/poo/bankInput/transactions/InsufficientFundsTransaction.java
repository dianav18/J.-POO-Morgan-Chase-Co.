package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Insufficient funds transaction.
 */
@Getter
public final class InsufficientFundsTransaction extends Transaction {
    private final String description;
    private final int timestamp;

    /**
     * Instantiates a new Insufficient funds transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
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
