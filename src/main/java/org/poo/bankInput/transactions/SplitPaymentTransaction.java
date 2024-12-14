package org.poo.bankInput.transactions;

import lombok.Getter;

import java.util.List;

@Getter
public class SplitPaymentTransaction extends Transaction{
    private final String currency;
    private final List<String> involvedAccounts;
    private final double amountPerAccount;
    private final double totalAmount;

    public SplitPaymentTransaction(final int timestamp, final String currency, final List<String> involvedAccounts, final double amount, final double totalAmount) {
        super(timestamp, "Split payment");
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.amountPerAccount = amount;
        this.totalAmount = totalAmount;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
