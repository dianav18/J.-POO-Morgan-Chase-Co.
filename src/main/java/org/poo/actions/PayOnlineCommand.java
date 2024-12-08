package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;

import java.util.List;

public class PayOnlineCommand implements CommandHandler {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciant;
    private final String email;
    private final List<User> users;
    private final CurrencyConverter currencyConverter;

    public PayOnlineCommand(final String cardNumber, final double amount, final String currency, final int timestamp, final String description,
                            final String commerciant, final String email, final List<User> users, final CurrencyConverter currencyConverter) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean cardFound = false;
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    for (final Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {
                            cardFound = true;
                            double finalAmount = amount;

                            if (!currency.equals(account.getCurrency())) {
                                finalAmount = currencyConverter.convert(amount, currency, account.getCurrency());
                            }
                            if (account.getBalance() >= finalAmount) {
                                account.setBalance(account.getBalance() - finalAmount);
                                return;
                            }
                        }
                    }
                }
                if (!cardFound) {
                    cardNotFoundOutput(output);
                    return;
                }
            }
        }
    }


    private void cardNotFoundOutput(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "payOnline");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", "Card not found");
        errorDetails.put("timestamp", timestamp);
    }
}
