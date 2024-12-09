package org.poo.handlers;

import org.poo.bankInput.Account;
import org.poo.bankInput.User;

import java.util.ArrayList;
import java.util.List;

public class AccountExtractor {
    public static List<Account> extractAccountsFromUsers(final List<User> users) {
        final List<Account> accounts = new ArrayList<>();
        for (final User user : users) {
            accounts.addAll(user.getAccounts());
        }
        return accounts;
    }
}
