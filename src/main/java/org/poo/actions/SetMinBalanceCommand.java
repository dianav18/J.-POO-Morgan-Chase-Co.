package org.poo.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * The type Set min balance command.
 */
public final class SetMinBalanceCommand implements CommandHandler {
    private final double amount;
    private final String accountIBAN;
    private final int timestamp;

    private List<User> users;

    /**
     * Instantiates a new Set min balance command.
     *
     * @param amount      the amount
     * @param accountIBAN the account iban
     * @param timestamp   the timestamp
     * @param users       the users
     */
    public SetMinBalanceCommand(final double amount, final String accountIBAN,
                                final int timestamp, final List<User> users) {
        this.amount = amount;
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final var user : users) {
            for (final var account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(accountIBAN)) {
                    account.setMinBalance(amount);
                    return;
                }
            }
        }
    }
}
