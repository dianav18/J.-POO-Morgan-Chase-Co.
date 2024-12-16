package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.SplitPaymentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;

import java.util.List;

public class SplitPaymentCommand implements CommandHandler {
    private final List<String> accountsForSplit;
    private final int timestamp;
    private final String currency;
    private final double amount;
    private final List<User> users;
    private final CurrencyConverter currencyConverter;

    public SplitPaymentCommand(final List<String> accountsForSplit, final int timestamp, final String currency, final double amount, final List<User> users, final CurrencyConverter currencyConverter) {
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
                    if (account.getIBAN().equals(accountIBAN)) {
                        foundAccount = true;
                        final double amountInAccountCurrency = account.getCurrency().equals(currency)
                                ? amountPerAccount
                                : currencyConverter.convert(amountPerAccount, currency, account.getCurrency());

                        if (account.getBalance() < amountInAccountCurrency) {
                            //System.out.println("Insufficient funds");
                            //user.addTransaction(new SplitPaymentTransaction(timestamp, currency, accountsForSplit, amountPerAccount, amount, true, accountIBAN));
                            //System.out.println("Insufficient funds");
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
                        if (account.getIBAN().equals(accountIBAN)) {
                            user.addTransaction(new SplitPaymentTransaction(timestamp, currency, accountsForSplit, amountPerAccount, amount, true, problematicAccountIBAN));
                        }
                    }
                }
            }
            return;
        }

        for (final String accountIBAN : accountsForSplit) {
            for (final User user : users) {
                for (final Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(accountIBAN)) {
                        final double amountInAccountCurrency = account.getCurrency().equals(currency)
                                ? amountPerAccount
                                : currencyConverter.convert(amountPerAccount, currency, account.getCurrency());

                        account.setBalance(account.getBalance() - amountInAccountCurrency);
                        user.addTransaction(new SplitPaymentTransaction(timestamp, currency, accountsForSplit, amountPerAccount, amount, false, accountIBAN));
                    }
                }
            }
        }
    }
}
