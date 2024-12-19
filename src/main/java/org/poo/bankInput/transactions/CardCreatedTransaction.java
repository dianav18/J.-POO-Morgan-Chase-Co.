package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Card created transaction.
 */
@Getter
public final class CardCreatedTransaction extends Transaction {
    private final String account;
    private final String card;
    private final String cardHolder;

    /**
     * Instantiates a new Card created transaction.
     *
     * @param timestamp  the timestamp
     * @param account    the account
     * @param card       the card
     * @param cardHolder the card holder
     */
    public CardCreatedTransaction(final int timestamp, final String account,
                                  final String card, final String cardHolder) {
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
