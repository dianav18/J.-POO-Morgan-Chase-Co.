package org.poo.bankInput.transactions;

public class CardFrozenTransaction extends Transaction {
    public CardFrozenTransaction(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
