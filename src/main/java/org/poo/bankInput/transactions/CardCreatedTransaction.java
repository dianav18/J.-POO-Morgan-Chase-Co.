package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class CardCreatedTransaction extends Transaction {
    private final String account;
    private final String card;
    private final String cardHolder;

    public CardCreatedTransaction(final int timestamp, final String account, final String card, final String cardHolder) {
        super(timestamp, "New card created");
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
