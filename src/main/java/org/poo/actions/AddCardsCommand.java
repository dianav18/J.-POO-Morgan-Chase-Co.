package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.utils.Utils;

import java.util.List;

import org.poo.bankInput.transactions.CardCreatedTransaction;

public final class AddCardsCommand implements CommandHandler {
    private final String accountIBAN;
    private final String email;
    private final boolean isOneTime;
    private final int timestamp;
    private final List<User> users;

    public AddCardsCommand(final String accountIBAN, final String email,
                           final boolean isOneTime, final int timestamp, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.email = email;
        this.isOneTime = isOneTime;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean userFound = false;
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            return;
        }


        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        final String cardNumber = Utils.generateCardNumber();
                        final Card newCard = new Card(cardNumber, isOneTime);
                        account.addCard(newCard);
                        account.addTransaction(new CardCreatedTransaction(timestamp,
                                account.getAccountIBAN(), cardNumber, user.getEmail()));
                    }
                }
            }
        }
    }
}
