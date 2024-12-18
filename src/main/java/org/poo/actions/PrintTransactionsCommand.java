package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.transactions.Transaction;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.TransactionPrinter;
import org.poo.handlers.CommandHandler;

import java.util.List;

public final class PrintTransactionsCommand implements CommandHandler {
    private final String email;
    private final int timestamp;
    private final List<User> users;

    public PrintTransactionsCommand(final String email, final int timestamp,
                                    final List<User> users) {
        this.email = email;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                final ObjectNode commandOutput = output.addObject();
                commandOutput.put("command", "printTransactions");
                commandOutput.put("timestamp", timestamp);

                final ArrayNode transactionsOutput = commandOutput.putArray("output");

                final TransactionPrinter printer = new TransactionPrinter(transactionsOutput);

                for (final Transaction transaction : user.getTransactions()) {
                    transaction.accept(printer);
                }

                return;
            }
        }
    }

}
