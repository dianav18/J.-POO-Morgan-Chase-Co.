package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction for a payment made using a card.
 * This transaction includes details about the amount paid, the commerciant involved,
 * and a description of the transaction.
 */
@Getter
public final class CardPaymentTransaction extends Transaction {
    private final double amount;
    private final String commerciant;
    private final String description;

    /**
     * Instantiates a new Card payment transaction.
     *
     * @param timestamp   the timestamp of the transaction, indicating when the payment occurred.
     * @param description a description of the payment purpose.
     * @param amount      the amount of money involved in the transaction.
     * @param commerciant the name of the commerciant where the payment was made.
     */
    public CardPaymentTransaction(final int timestamp, final String description,
                                  final double amount, final String commerciant) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
        this.description = description;
    }

    /**
     * Accepts a {@link TransactionVisitor} to process this transaction type.
     *
     * @param visitor the visitor object that processes the transaction.
     */
    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
