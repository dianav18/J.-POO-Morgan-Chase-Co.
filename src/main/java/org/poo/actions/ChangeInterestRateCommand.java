package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.ChangeInterestRateTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class ChangeInterestRateCommand implements CommandHandler {
    private final String accountIBAN;
    private final double newInterestRate;
    private final int timestamp;

    private final List<User> users;

    public ChangeInterestRateCommand(final String accountIBAN, final double newInterestRate, final int timestamp, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.newInterestRate = newInterestRate;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean accountIsSavinAccount = false;
        for (final User user : users) {
            for (final var account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    if (account.getType().equals("savings")) {
                        accountIsSavinAccount = true;
                        final SavingsAccount savingsAccount = (SavingsAccount) account;
                        user.addTransaction(new ChangeInterestRateTransaction(timestamp, newInterestRate, "Interest rate of the account changed to " + newInterestRate));
                        savingsAccount.addInterest(newInterestRate);
                        return;
                    }
                }
            }
        }
        if (!accountIsSavinAccount) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "changeInterestRate");
            outputNode.put("timestamp", timestamp);
            final ObjectNode outputNode2 = outputNode.putObject("output");
            outputNode2.put("description", "This is not a savings account");
            outputNode2.put("timestamp", timestamp);
        }
    }
}
