package org.poo.bankInput.transactions;

public class CardFrozenTransaction extends Transaction {

    public CardFrozenTransaction(final int timestamp) {
        super(timestamp, "The card is frozen");
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
