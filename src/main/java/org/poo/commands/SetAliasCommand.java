package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * The type Set alias command.
 */
public final class SetAliasCommand implements CommandHandler {
    private final String email;
    private final String alias;
    private final String accountIBAN;

    private final List<User> users;

    /**
     * Instantiates a new Set alias command.
     *
     * @param email       the email
     * @param alias       the alias
     * @param accountIBAN the account iban
     * @param users       the users
     */
    public SetAliasCommand(final String email, final String alias,
                           final String accountIBAN, final List<User> users) {
        this.email = email;
        this.alias = alias;
        this.accountIBAN = accountIBAN;
        this.users = users;
    }


    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        account.setAlias(alias);
                    }
                }
            }
        }
    }
}
