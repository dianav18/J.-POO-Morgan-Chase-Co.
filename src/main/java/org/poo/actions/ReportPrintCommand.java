package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.Transaction;
import org.poo.bankInput.transactions.TransactionPrinter;
import org.poo.handlers.CommandHandler;

import java.util.ArrayList;
import java.util.List;

public class ReportPrintCommand implements CommandHandler {
    private final int startTimestamp;
    private final int endTimestamp;
    private final String accountIBAN;
    private final int timestamp;
    private List<Transaction> transactions;
    private final List<User> users;

    public ReportPrintCommand(final int startTimestamp, final int endTimestamp, final String accountIBAN, final int timestamp, final List<User> users) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.users = users;
        this.transactions = new ArrayList<>();
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean accountFound = false;
        double balance = 0;
        String currency = null;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    balance = account.getBalance();
                    currency = account.getCurrency();
                    transactions = user.getTransactions();
                    accountFound = true;
                    break;
                }
            }
            if (accountFound) {
                break;
            }
        }

        if (!accountFound) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "report");
            outputNode.put("timestamp", timestamp);
            final ObjectNode reportNode = outputNode.putObject("output");
            reportNode.put("description", "Account not found");
            reportNode.put("timestamp", timestamp);
            return;
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode reportNode = objectMapper.createObjectNode();

        reportNode.put("IBAN", accountIBAN);
        reportNode.put("balance", balance);
        reportNode.put("currency", currency);

        final ArrayNode transactionsArray = objectMapper.createArrayNode();
        for (final Transaction transaction : transactions) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                transaction.accept(new TransactionPrinter(transactionsArray));
            }
        }

        reportNode.set("transactions", transactionsArray);

        final ObjectNode outputNode = output.addObject();
        outputNode.put("command", "report");
        outputNode.set("output", reportNode);
        outputNode.put("timestamp", timestamp);
    }
}
