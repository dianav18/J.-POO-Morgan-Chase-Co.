package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class AddInterestCommand implements CommandHandler {
    private final int timestamp;
    private final String accountIBAN;
    private double interestRate;

    private final List<User> users;

    public AddInterestCommand(final int timestamp, final String accountIBAN, final double interestRate, final List<User> users) {
        this.timestamp = timestamp;
        this.accountIBAN = accountIBAN;
        this.interestRate = interestRate;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean accountIsSavinAccount = false;
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    if (account.getType().equals("savings")) {
                        accountIsSavinAccount = true;
                        final SavingsAccount savingsAccount = (SavingsAccount) account;
                        savingsAccount.addInterest(interestRate);
                        return;
                    }
                }
            }
        }
        if (!accountIsSavinAccount) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "addInterest");
            outputNode.put("timestamp", timestamp);
            final ObjectNode outputNode2 = outputNode.putObject("output");
            outputNode2.put("description", "This is not a savings account");
            outputNode2.put("timestamp", timestamp);
        }
    }
}
