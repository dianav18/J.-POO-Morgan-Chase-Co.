package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class CardDestroyedTransaction extends Transaction {
    private final String account;
    private final String card;
    private final String email;

    public CardDestroyedTransaction(final int timestamp, final String description, final String account, final String card, final String email) {
        super(timestamp, description);
        this.account = account;
        this.card = card;
        this.email = email;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
