package org.poo.bankInput.transactions;

public class CannotDeleteAccountTransaction extends Transaction {
    public CannotDeleteAccountTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
