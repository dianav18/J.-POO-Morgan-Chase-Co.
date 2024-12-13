package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardFrozenTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class CheckCardStatusCommand implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;
    private List<User> users;

    public CheckCardStatusCommand(final String cardNumber, final int timestamp, final List<User> users) {
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final var user : users) {
            for (final var account : user.getAccounts()) {
                for (final var card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        if (account.getBalance() <= account.getMinBalance()) {
                            card.freeze();
                            //user.addTransaction(new CardFrozenTransaction(timestamp));
                        }
                        if (account.getBalance() - account.getMinBalance() <= 30) {
                            card.warning();
                        }
                    }
                }
            }
        }
    }
}
