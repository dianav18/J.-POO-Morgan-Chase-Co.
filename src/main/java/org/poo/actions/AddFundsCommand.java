package org.poo.actions;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

public final class AddFundsCommand implements CommandHandler {
    private final String account;
    private final double amount;
    private final int timestamp;
    private final List<User> users;

    public AddFundsCommand(final String account, final double amount,
                           final int timestamp, final List<User> users) {
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
        this.users = users;
    }


    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final Account userAccount : user.getAccounts()) {
                if (userAccount.getAccountIBAN().equals(account)) {
                    userAccount.setBalance(userAccount.getBalance() + amount);
                }
            }
        }
    }
}
