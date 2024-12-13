package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.utils.Utils;

import java.util.List;
import java.util.UUID;

import org.poo.bankInput.transactions.CardCreatedTransaction;

public class AddCards implements CommandHandler {
    private final String accountIBAN;
    private final String email;
    private final String cardNumber;
    private final boolean isOneTime;
    private final int timestamp;
    private final List<User> users;


    public AddCards(final String accountIBAN, final String email, final String cardNumber,
                    final boolean isOneTime, final int timestamp, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.email = email;
        this.cardNumber = Utils.generateCardNumber();
        this.isOneTime = isOneTime;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(accountIBAN)) {
                        final Card newCard = new Card(cardNumber, isOneTime);
                        account.addCard(newCard);
                        user.addTransaction(new CardCreatedTransaction(timestamp, account.getIBAN(), cardNumber, user.getEmail()));
                    }
                }
            }
        }
    }
}
