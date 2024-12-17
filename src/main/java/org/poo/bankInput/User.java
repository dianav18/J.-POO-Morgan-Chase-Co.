package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;
import org.poo.bankInput.transactions.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts;
    private List<Transaction> transactions;
    //  private List<Commerciant> commerciants;

    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        //    commerciants = new ArrayList<>();
    }

    public void addAccount(final Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(final Account account) {
        this.accounts.remove(account);
    }

    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        final List<Transaction> output = new ArrayList<>();

        output.addAll(this.transactions);

        for (final Account account : accounts) {
            output.addAll(account.getTransactions());
        }

        output.sort(Comparator.comparingInt(Transaction::getTimestamp));
        return output;
    }

//    public void addCommerciant(final Commerciant commerciant) {
//        this.commerciants.add(commerciant);
//    }

}
