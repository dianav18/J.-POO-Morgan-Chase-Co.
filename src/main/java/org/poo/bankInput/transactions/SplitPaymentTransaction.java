package org.poo.bankInput.transactions;

import lombok.Getter;

import java.util.List;

/**
 * The type Split payment transaction.
 */
@Getter
public final class SplitPaymentTransaction extends Transaction {
    private final String currency;
    private final List<String> involvedAccounts;
    private final double amountPerAccount;
    private final double totalAmount;

    private final boolean showError;
    private final String problematicAccountIBAN;

    /**
     * Instantiates a new Split payment transaction.
     *
     * @param timestamp              the timestamp
     * @param currency               the currency
     * @param involvedAccounts       the involved accounts
     * @param amount                 the amount
     * @param totalAmount            the total amount
     * @param showError              the show error
     * @param problematicAccountIBAN the problematic account iban
     */
    public SplitPaymentTransaction(final int timestamp, final String currency,
                                   final List<String> involvedAccounts,
                                   final double amount, final double totalAmount,
                                   final boolean showError,
                                   final String problematicAccountIBAN) {
        super(timestamp, "Split payment");
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.amountPerAccount = amount;
        this.totalAmount = totalAmount;
        this.showError = showError;
        this.problematicAccountIBAN = problematicAccountIBAN;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
