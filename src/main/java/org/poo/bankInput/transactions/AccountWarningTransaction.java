package org.poo.bankInput.transactions;

public class AccountWarningTransaction extends Transaction {

    public AccountWarningTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
