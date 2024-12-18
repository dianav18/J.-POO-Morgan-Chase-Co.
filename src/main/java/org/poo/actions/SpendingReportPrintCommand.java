package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardPaymentTransaction;
import org.poo.bankInput.transactions.Transaction;
import org.poo.bankInput.transactions.TransactionPrinter;
import org.poo.handlers.CommandHandler;

import java.util.*;

public class SpendingReportPrintCommand implements CommandHandler {
    private final int startTimestamp;
    private final int endTimestamp;
    private final String accountIBAN;
    private final int timestamp;
    private List<Transaction> transactions;
    private final List<User> users;
    private List<Commerciant> commerciants;

    private Account account;

    public SpendingReportPrintCommand(final int startTimestamp, final int endTimestamp, final String accountIBAN, final int timestamp, final List<User> users) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.users = users;
        this.transactions = new ArrayList<>();
        this.commerciants = new ArrayList<>();
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean accountFound = false;
        double balance = 0;
        String currency = null;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    this.account = account;
                    balance = account.getBalance();
                    currency = account.getCurrency();
                    transactions = account.getTransactions();
                    commerciants = account.getCommerciants();
                    accountFound = true;
                    if (account.getType().equals("savings")) {
                        final ObjectNode outputNode = output.addObject();
                        outputNode.put("command", "spendingsReport");
                        final ObjectNode reportNode = outputNode.putObject("output");
                        reportNode.put("error", "This kind of report is not supported for a saving account");
                        outputNode.put("timestamp", timestamp);
                        return;
                    }
                    break;
                }
            }
            if (accountFound) {
                break;
            }
        }

        if (!accountFound) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "spendingsReport");
            outputNode.put("timestamp", timestamp);
            final ObjectNode reportNode = outputNode.putObject("output");
            reportNode.put("description", "Account not found");
            reportNode.put("timestamp", timestamp);
            return;
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode reportNode = objectMapper.createObjectNode();

        final Map<String, Double> spendings = new HashMap<>();
        for (final Transaction transaction : transactions) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                if (transaction instanceof CardPaymentTransaction) {
                    final CardPaymentTransaction cardPaymentTransaction = (CardPaymentTransaction) transaction;
                    final String commerciant = cardPaymentTransaction.getCommerciant();
                    final double amount = cardPaymentTransaction.getAmount();
                    if (spendings.containsKey(commerciant)) {
                        spendings.put(commerciant, spendings.get(commerciant) + amount);
                    } else {
                        spendings.put(commerciant, amount);
                    }
                }
            }
        }

        commerciants.sort(Comparator.comparing(Commerciant::getName));

        final ArrayNode spendingsArray = objectMapper.createArrayNode();
        for (final Commerciant commerciant : commerciants) {
            if (spendings.containsKey(commerciant.getName())) {
                final ObjectNode spendingNode = objectMapper.createObjectNode();
                spendingNode.put("commerciant", commerciant.getName());
                spendingNode.put("total", spendings.get(commerciant.getName()));
                spendingsArray.add(spendingNode);
            }
        }

        reportNode.set("commerciants", spendingsArray);

        final ArrayNode transactionsArray = objectMapper.createArrayNode();
        for (final Transaction transaction : account.getCommerciantTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                final TransactionPrinter transactionPrinter = new TransactionPrinter(transactionsArray);
                transaction.accept(transactionPrinter);
            }
        }

        reportNode.set("transactions", transactionsArray);

        reportNode.put("IBAN", accountIBAN);
        reportNode.put("balance", balance);
        reportNode.put("currency", currency);

        final ObjectNode outputNode = output.addObject();
        outputNode.put("command", "spendingsReport");
        outputNode.set("output", reportNode);
        outputNode.put("timestamp", timestamp);
    }
}
