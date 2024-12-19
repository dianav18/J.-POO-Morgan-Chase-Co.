package org.poo.bankInput.transactions;

/**
 * The type Account created transaction.
 */
public final class AccountCreatedTransaction extends Transaction {
    /**
     * Instantiates a new Account created transaction.
     *
     * @param timestamp the timestamp
     */
    public AccountCreatedTransaction(final int timestamp) {
        super(timestamp, "New account created");
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
