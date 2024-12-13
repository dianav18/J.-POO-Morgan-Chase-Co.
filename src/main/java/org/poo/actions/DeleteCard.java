package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class DeleteCard implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;
    private final List<User> users;

    public DeleteCard(final String cardNumber, final int timestamp, final List<User> users) {
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
        this.users = users;
    }


    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final var account : user.getAccounts()) {
                for (final var card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        user.addTransaction(new CardDestroyedTransaction(
                                timestamp,
                                "The card has been destroyed",
                                account.getIBAN(),
                                card.getCardNumber(),
                                user.getEmail()
                        ));
                        card.destroy();
                        account.removeCard(card);
                        return;
                    }
                }
            }
        }
    }
}
