package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
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
        boolean accountDeleted = false;
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(accountIBAN)) {
                        if (account.getBalance() == 0) {
                            for (final Card card : account.getCards()) {
                                card.destroy();
                                user.addTransaction(new CardDestroyedTransaction(
                                        timestamp,
                                        "The card has been destroyed",
                                        accountIBAN,
                                        card.getCardNumber(),
                                        email
                                ));
                            }
                            accountDeleted = true;
                            user.removeAccount(account);
                            break;
                        }
                    }
                }

            }
        }
        if (accountDeleted) {
            outputPrint(output);
        } else {
            outputError(output);
        }
    }


    private void outputError(final ArrayNode output) {
        final ObjectNode errorOutput = output.addObject();
        errorOutput.put("command", "deleteAccount");
        errorOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = errorOutput.putObject("output");
        errorDetails.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
        errorDetails.put("timestamp", timestamp);
    }

    private void outputPrint(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "deleteAccount");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode successDetails = commandOutput.putObject("output");
        successDetails.put("success", "Account deleted");
        successDetails.put("timestamp", timestamp);
    }
}
