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
                                account.addTransaction(new CardFrozenTransaction(timestamp, "The card is frozen"));
                                System.out.println(cardNumber + " " + timestamp + " the card is frozen");
                                return;
                            }

                            cardFound = true;
                            double finalAmount = amount;

                            if (!currency.equals(account.getCurrency())) {
                                finalAmount = currencyConverter.convert(amount, currency, account.getCurrency());
                            }

                            if (account.getBalance() < finalAmount) {
                                account.addTransaction(new InsufficientFundsTransaction(timestamp, "Insufficient funds"));
                                System.out.println(cardNumber + " " + timestamp + " insufficient funds");
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
                                    account.addCommerciantTransaction(new CommerciantTransaction(newCommerciant.getName(), finalAmount, timestamp));
                                }
                                account.addTransaction(new CardPayment(timestamp, description, finalAmount, commerciant, timestamp));
                                System.out.println(cardNumber + " " + timestamp + " success");
                                if (card.isOneTime()) {
                                    account.addTransaction(new CardDestroyedTransaction(timestamp, "Card destroyed", account.getIBAN(), card.getCardNumber(), user.getEmail()));
                                    account.removeCard(card);
                                    final Card newCard = new Card(Utils.generateCardNumber(), true);
                                    account.addCard(newCard);
                                    account.addTransaction(new CardCreatedTransaction(timestamp, account.getIBAN(), newCard.getCardNumber(), user.getEmail()));
                                }
                                return;
                            }
                        }
                    }
                }
                if (!cardFound) {
                    System.out.println(cardNumber + " " + timestamp + " card not found");
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
