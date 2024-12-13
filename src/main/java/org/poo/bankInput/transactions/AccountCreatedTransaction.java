package org.poo.bankInput.transactions;

public class AccountCreatedTransaction extends Transaction {
    public AccountCreatedTransaction(final int timestamp) {
        super(timestamp, "New account created");
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}