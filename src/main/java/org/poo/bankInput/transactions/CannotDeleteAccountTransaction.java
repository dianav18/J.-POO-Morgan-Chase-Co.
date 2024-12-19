package org.poo.bankInput.transactions;

/**
 * The type Cannot delete account transaction.
 */
public final class CannotDeleteAccountTransaction extends Transaction {
    /**
     * Instantiates a new Cannot delete account transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public CannotDeleteAccountTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
