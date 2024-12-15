package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Commerciant {
    private String name;
    private double totalSpent;

    public Commerciant(final String name) {
        this.name = name;
        totalSpent = 0;
    }

    public void addSpentAmount(final double amount) {
        totalSpent += amount;
    }
}
