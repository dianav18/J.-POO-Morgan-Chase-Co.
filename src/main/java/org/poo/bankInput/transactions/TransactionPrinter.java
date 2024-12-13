package org.poo.bankInput.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TransactionPrinter implements TransactionVisitor {
    private final ArrayNode output;

    public TransactionPrinter(final ArrayNode output) {
        this.output = output;
    }

    @Override
    public void visit(final AccountCreatedTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", transaction.getDescription());
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final SentTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", transaction.getDescription());
        node.put("amount", transaction.getAmount() + " " + transaction.getCurrency());
        node.put("senderIBAN", transaction.getSenderIBAN());
        node.put("receiverIBAN", transaction.getReceiverIBAN());
        node.put("timestamp", transaction.getTimestamp());
        node.put("transferType", "sent");
    }

    @Override
    public void visit(final CardCreatedTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("account", transaction.getAccount());
        node.put("card", transaction.getCard());
        node.put("cardHolder", transaction.getCardHolder());
        node.put("description", transaction.getDescription());
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final CardPayment transaction) {
        final ObjectNode node = output.addObject();
        node.put("amount", transaction.getAmount());
        node.put("commerciant", transaction.getCommerciant());
        node.put("description", "Card payment");
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final InsufficientFundsTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", "Insufficient funds");
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final CardDestroyedTransaction cardDestroyedTransaction) {
        final ObjectNode node = output.addObject();
        node.put("account", cardDestroyedTransaction.getAccount());
        node.put("card", cardDestroyedTransaction.getCard());
        node.put("cardHolder", cardDestroyedTransaction.getEmail());
        node.put("description", "The card has been destroyed");
        node.put("timestamp", cardDestroyedTransaction.getTimestamp());
    }

    @Override
    public void visit(final CardFrozenTransaction cardFrozenTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description", "The card is frozen");
        node.put("timestamp", cardFrozenTransaction.getTimestamp());
    }
}
