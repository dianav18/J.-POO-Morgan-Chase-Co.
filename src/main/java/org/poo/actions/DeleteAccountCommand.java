package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CannotDeleteAccountTransaction;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

public final class DeleteAccountCommand implements CommandHandler {
    private final String accountIBAN;
    private final int timestamp;
    private final String email;
    private final List<User> users;

    public DeleteAccountCommand(final String accountIBAN, final int timestamp,
                                final String email, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.email = email;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        if (account.getBalance() == 0) {
                            for (final Card card : account.getCards()) {
                                card.destroy();
                                account.addTransaction(new CardDestroyedTransaction(
                                        timestamp,
                                        "The card has been destroyed",
                                        accountIBAN,
                                        card.getCardNumber(),
                                        email
                                ));
                            }
                            user.removeAccount(account);
                            break;
                        } else {
                            account.addTransaction(new CannotDeleteAccountTransaction(
                                    timestamp,
                                    "The account couldn't be deleted because it has"
                                            + "a balance greater than 0"
                            ));
                            outputError(output);
                            return;
                        }
                    }
                }

            }
        }
        outputPrint(output);
    }


    private void outputError(final ArrayNode output) {
        final ObjectNode errorOutput = output.addObject();
        errorOutput.put("command", "deleteAccount");
        errorOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = errorOutput.putObject("output");
        errorDetails.put("error",
                "Account couldn't be deleted - see org.poo.transactions for details");
        errorDetails.put("timestamp", timestamp);
    }

    private void outputPrint(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "deleteAccount");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode successDetails = commandOutput.putObject("output");
        successDetails.put("success", "Account deleted");
        successDetails.put("timestamp", timestamp);
    }
}
