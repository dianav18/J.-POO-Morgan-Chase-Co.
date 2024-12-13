package org.poo.bankInput.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(final Transaction transaction) {
        transactions.add(transaction);
    }

    public void printTransactions(final ArrayNode output) {
        final TransactionPrinter printer = new TransactionPrinter(output);
        for (final Transaction transaction : transactions) {
            transaction.accept(printer);
        }
    }
}
