package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class DeleteAccount implements CommandHandler {
    private final String accountIBAN;
    private final int timestamp;
    private final String email;
    private final List<User> users;

    public DeleteAccount(final String accountIBAN, final int timestamp, final String email, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.email = email;
        this.users = users;
    }

    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                   if (account.getIBAN().equals(accountIBAN) && account.getBalance() == 0) {
                          user.getAccounts().remove(account);
                   }
                }
                return;
            }
        }
    }
}
