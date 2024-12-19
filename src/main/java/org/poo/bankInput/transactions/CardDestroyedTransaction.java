package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Card destroyed transaction.
 */
@Getter
public final class CardDestroyedTransaction extends Transaction {
    private final String account;
    private final String card;
    private final String email;

    /**
     * Instantiates a new Card destroyed transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     * @param account     the account
     * @param card        the card
     * @param email       the email
     */
    public CardDestroyedTransaction(final int timestamp, final String description,
                                    final String account, final String card, final String email) {
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
