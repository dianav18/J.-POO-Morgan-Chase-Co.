package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Account {
    private String IBAN;
    private String currency;
    private String type;
    private double balance;
    private List<Card> cards;
    private String alias;
    private double minBalance;

    public Account(final String IBAN, final String currency, final String type) {
        this.IBAN = IBAN;
        this.currency = currency;
        this.type = type;
        this.balance = 0;
        this.cards = new ArrayList<>();
        this.minBalance = 0;
    }

    public void addCard(final Card card) {
        this.cards.add(card);
    }

    public void removeCard(final Card card) {
        this.cards.remove(card);
    }
}
