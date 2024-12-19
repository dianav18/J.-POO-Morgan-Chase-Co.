package org.poo.bankInput.transactions;

/**
 * The type Card frozen transaction.
 */
public final class CardFrozenTransaction extends Transaction {
    /**
     * Instantiates a new Card frozen transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public CardFrozenTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
