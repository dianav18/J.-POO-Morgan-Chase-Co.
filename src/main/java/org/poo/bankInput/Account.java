package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;
import org.poo.bankInput.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account.
 */
/*
    * Class that represents an account.
    * An account has an IBAN, a currency, a type, a balance,
    * a list of cards, an alias, a minimum balance,
    * a list of commerciants, a list of commerciant
    * transactions and a list of transactions.
    * Class is extended by ClassicAccount and SavingsAccount.
 */
@Getter
@Setter
public class Account {
    private String accountIBAN;
    private String currency;
    private String type;
    private double balance;
    private List<Card> cards;
    private String alias;
    private double minBalance;
    private List<Commerciant> commerciants;
    private List<Transaction> commerciantTransactions;
    private List<Transaction> transactions;

    /**
     * Instantiates a new Account.
     *
     * @param accountIBAN the account iban
     * @param currency    the currency
     * @param type        the type
     */
    public Account(final String accountIBAN, final String currency, final String type) {
        this.accountIBAN = accountIBAN;
        this.currency = currency;
        this.type = type;
        this.balance = 0;
        this.cards = new ArrayList<>();
        this.minBalance = 0;
        commerciants = new ArrayList<>();
        commerciantTransactions = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    /**
     * Add card.
     *
     * @param card the card
     */
    public void addCard(final Card card) {
        this.cards.add(card);
    }

    /**
     * Remove card.
     *
     * @param card the card
     */
    public void removeCard(final Card card) {
        this.cards.remove(card);
    }

    /**
     * Add commerciant.
     *
     * @param commerciant the commerciant
     */
    public void addCommerciant(final Commerciant commerciant) {
        this.commerciants.add(commerciant);
    }

    /**
     * Add commerciant transaction.
     *
     * @param transaction the transaction
     */
    public void addCommerciantTransaction(final Transaction transaction) {
        this.commerciantTransactions.add(transaction);
    }

    /**
     * Add transaction.
     *
     * @param transaction the transaction
     */
    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }
}
