package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.transactions.Transaction;
import org.poo.handlers.CommandHandler;

public class ReportCommand implements CommandHandler {
    private final int startTimestamp;
    private final int endTimestamp;
    private final String accountIBAN;

    private final double balance;
    private final String currency;
    private final Transaction[] transactions;

    public ReportCommand(final int startTimestamp, final int endTimestamp, final String accountIBAN, final double balance, final String currency, final Transaction[] transactions) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.accountIBAN = accountIBAN;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }

    @Override
    public void execute(final ArrayNode output) {

    }
}
