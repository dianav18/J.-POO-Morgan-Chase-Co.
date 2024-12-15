package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.*;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;
import org.poo.utils.Utils;

import java.sql.SQLOutput;
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
                            //System.out.println("here");
//                            if (card.getStatus().equals("warning")) {
//                                //System.out.println("warning");
//                                user.addTransaction(new AccountWarningTransaction(timestamp, "You have reached the minimum amount of funds"));
//                                return;
//                            }

                            if (card.getStatus().equals("frozen")) {
                               // System.out.println("frozen");
                                user.addTransaction(new CardFrozenTransaction(timestamp, "The card is frozen"));
                                return;
                            }

                            cardFound = true;
                            double finalAmount = amount;

                            if (!currency.equals(account.getCurrency())) {
                                finalAmount = currencyConverter.convert(amount, currency, account.getCurrency());
                            }

                            if (account.getBalance() < finalAmount) {
                                user.addTransaction(new InsufficientFundsTransaction(timestamp, "Insufficient funds"));
                                return;
                            }

                            if (account.getBalance() >= finalAmount) {
                                account.setBalance(account.getBalance() - finalAmount);
                                boolean found = false;
                                for (final Commerciant userCommerciant : account.getCommerciants()) {
                                    if (userCommerciant.getName().equalsIgnoreCase(commerciant)) {
                                        userCommerciant.addSpentAmount(finalAmount);
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    final Commerciant newCommerciant = new Commerciant(commerciant);
                                    newCommerciant.addSpentAmount(finalAmount);
                                    account.addCommerciant(newCommerciant);
                                    account.addTransaction(new CommerciantTransaction(newCommerciant.getName(), finalAmount, timestamp));
                                }
                                user.addTransaction(new CardPayment(timestamp, description, finalAmount, commerciant, timestamp));
                                if (card.isOneTime()) {
                                    account.removeCard(card);
                                    final Card newCard = new Card(Utils.generateCardNumber(), true);
                                    account.addCard(newCard);
                                    user.addTransaction(new CardCreatedTransaction(timestamp, account.getIBAN(), newCard.getCardNumber(), user.getEmail()));
                                }
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
