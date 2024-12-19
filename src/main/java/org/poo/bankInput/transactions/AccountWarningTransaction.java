package org.poo.bankInput.transactions;

/**
 * The type Account warning transaction.
 */
public final class AccountWarningTransaction extends Transaction {

    /**
     * Instantiates a new Account warning transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public AccountWarningTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
