package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.ClassicAccount;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.transactions.AccountCreatedTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.utils.Utils;

import java.util.List;

public final class AddAccountCommand implements CommandHandler {
    private final String email;
    private final String currency;
    private final String accountType;
    private final double interestRate; // Doar pentru conturi de economii
    private final int timestamp;
    private final List<User> users;

    public AddAccountCommand(final String email, final String currency,
                             final String accountType, final double interestRate,
                             final int timestamp, final List<User> users) {
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                final Account newAccount;

                final String accountIBAN = Utils.generateIBAN();

                if (accountType.equals("classic")) {
                    newAccount = new ClassicAccount(accountIBAN, currency);
                } else if (accountType.equals("savings")) {
                    newAccount = new SavingsAccount(accountIBAN, currency, interestRate);
                } else {
                    throw new IllegalArgumentException("Invalid account type: " + accountType);
                }

                user.addAccount(newAccount);
                newAccount.addTransaction(new AccountCreatedTransaction(timestamp));
                return;
            }
        }

    }

}
