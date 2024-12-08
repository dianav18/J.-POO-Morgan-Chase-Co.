package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts;

    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(final Account account) {
        this.accounts.add(account);
    }

}
