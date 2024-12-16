package org.poo.bankInput.transactions;

import lombok.Getter;

import java.util.List;

@Getter
public class SplitPaymentTransaction extends Transaction{
    private final String currency;
    private final List<String> involvedAccounts;
    private final double amountPerAccount;
    private final double totalAmount;

    private final boolean showError;
    private final String problematicAccountIBAN;

    public SplitPaymentTransaction(final int timestamp, final String currency, final List<String> involvedAccounts, final double amount, final double totalAmount, final boolean showError, final String problematicAccountIBAN) {
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
