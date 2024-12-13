package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class SentTransaction extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;

    public SentTransaction(final int timestamp, final String description, final String senderIBAN, final String receiverIBAN, final double amount, final String currency) {
        super(timestamp, description);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}