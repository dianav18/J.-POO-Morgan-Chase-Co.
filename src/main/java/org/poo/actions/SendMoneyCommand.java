package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;

import java.util.List;

public class SendMoneyCommand implements CommandHandler {
    private final String senderIBAN;
    private final double amount;
    private final String receiverIBAN;
    private final int timestamp;
    private final String description;

    private final List<Account> accounts;
    private final CurrencyConverter currencyConverter;

    private final List<User> users;


    public SendMoneyCommand(final String senderIBAN, final double amount, final String receiverIBAN, final int timestamp, final String description, final List<Account> accounts, final CurrencyConverter currencyConverter, final List<User> users) {
        this.senderIBAN = senderIBAN;
        this.amount = amount;
        this.receiverIBAN = receiverIBAN;
        this.timestamp = timestamp;
        this.description = description;
        this.accounts = accounts;
        this.currencyConverter = currencyConverter;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        Account senderAccount = null;
        Account receiverAccount = null;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(senderIBAN)) {
                    senderAccount = account;
                }
                if (account.getIBAN().equals(receiverIBAN)) {
                    receiverAccount = account;
                }
            }
        }

        if (senderAccount == null || receiverAccount == null) {
            return;
        }

        if (senderAccount.getBalance() < amount) {
            return;
        }

        double convertedAmount = amount;
        if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
            convertedAmount = currencyConverter.convert(amount, senderAccount.getCurrency(), receiverAccount.getCurrency());
            if (convertedAmount == -1) {
                return;
            }
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + convertedAmount);

    }
}
