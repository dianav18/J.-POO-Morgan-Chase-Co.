package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.AccountWarningTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

public final class CheckCardStatusCommand implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;
    private final List<User> users;

    public CheckCardStatusCommand(final String cardNumber, final int timestamp,
                                  final List<User> users) {
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean cardFound = false;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        cardFound = true;
                        if (account.getBalance() <= account.getMinBalance()) {
                            card.freeze();
                            account.addTransaction(new AccountWarningTransaction(timestamp,
                                    "You have reached the minimum amount of funds"));
                            return;
                        }
                    }
                }
            }
        }

        if (!cardFound) {
            // Card not found error
            final ObjectNode errorOutput = output.addObject();
            errorOutput.put("command", "checkCardStatus");
            errorOutput.put("timestamp", timestamp);
            final ObjectNode errorDetails = errorOutput.putObject("output");
            errorDetails.put("description", "Card not found");
            errorDetails.put("timestamp", timestamp);
        }
    }
}
