package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(accountIBAN) && account.getBalance() == 0) {
                        user.removeAccount(account);
                        final ObjectNode commandOutput = output.addObject();
                        commandOutput.put("command", "deleteAccount");
                        commandOutput.put("timestamp", timestamp);
                        final ObjectNode successDetails = commandOutput.putObject("output");
                        successDetails.put("success", "Account deleted");
                        successDetails.put("timestamp", timestamp);
                        return;
                    }
                }
            }
        }
    }
}
