package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.SplitPaymentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;

import java.util.List;

/**
 * The type Split payment command.
 */
public final class SplitPaymentCommand implements CommandHandler {
    private final List<String> accountsForSplit;
    private final int timestamp;
    private final String currency;
    private final double amount;
    private final List<User> users;
    private final CurrencyConverter currencyConverter;

    /**
     * Instantiates a new Split payment command.
     *
     * @param accountsForSplit  the accounts for split
     * @param timestamp         the timestamp
     * @param currency          the currency
     * @param amount            the amount
     * @param users             the users
     * @param currencyConverter the currency converter
     */
    public SplitPaymentCommand(final List<String> accountsForSplit,
                               final int timestamp, final String currency,
                               final double amount, final List<User> users,
                               final CurrencyConverter currencyConverter) {
        this.accountsForSplit = accountsForSplit;
        this.timestamp = timestamp;
        this.currency = currency;
        this.amount = amount;
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute(final ArrayNode output) {
        final int numberOfAccounts = accountsForSplit.size();
        final double amountPerAccount = amount / numberOfAccounts;

        boolean hasError = false;
        String problematicAccountIBAN = null;

        for (final String accountIBAN : accountsForSplit) {
            boolean foundAccount = false;
            for (final User user : users) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        foundAccount = true;
                        final double amountInAccountCurrency
                                = account.getCurrency().equals(currency)
                                ? amountPerAccount
                                : currencyConverter.convert(
                                        amountPerAccount, currency, account.getCurrency());

                        if (account.getBalance() < amountInAccountCurrency) {
                            problematicAccountIBAN = accountIBAN;
                            hasError = true;
                        }
                    }
                }
            }

            if (!foundAccount) {
                return;
            }
        }

        if (hasError) {
            for (final String accountIBAN : accountsForSplit) {
                for (final User user : users) {
                    for (final Account account : user.getAccounts()) {
                        if (account.getAccountIBAN().equals(accountIBAN)) {
                            account.addTransaction(new SplitPaymentTransaction(
                                    timestamp, currency, accountsForSplit,
                                    amountPerAccount, amount, true,
                                    problematicAccountIBAN));
                        }
                    }
                }
            }
            return;
        }

        for (final String accountIBAN : accountsForSplit) {
            for (final User user : users) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        final double amountInAccountCurrency
                                = account.getCurrency().equals(currency)
                                ? amountPerAccount
                                : currencyConverter.convert(amountPerAccount,
                                currency, account.getCurrency());

                        account.setBalance(account.getBalance() - amountInAccountCurrency);
                        account.addTransaction(new SplitPaymentTransaction(timestamp,
                                currency, accountsForSplit, amountPerAccount,
                                amount, false, accountIBAN));
                    }
                }
            }
        }
    }
}
