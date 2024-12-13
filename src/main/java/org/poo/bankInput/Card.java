package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String cardNumber;
    private String status;
    private boolean isOneTime;

    public Card(final String cardNumber, final boolean isOneTime) {
        this.cardNumber = cardNumber;
        this.status = "active";
        this.isOneTime = isOneTime;
    }

    public boolean useCard() {
        if (isOneTime) {
            this.status = "expired";
            return true;
        }
        return false;
    }

    public void destroy() {
        this.status = "destroyed";
    }

    public void freeze() {
        this.status = "frozen";
    }

    public void warning() {
        this.status = "warning";
    }

}