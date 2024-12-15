//package org.poo.bankInput.transactions;
//
//import lombok.Getter;
//
//@Getter
//public class ChangeInterestRateTransaction extends Transaction {
//    private final String accountIBAN;
//    private final double interestRate;
//    private final String description;
//
//    public ChangeInterestRateTransaction(final int timestamp, final String accountIBAN, final double interestRate, final String description) {
//        super(timestamp, description);
//        this.accountIBAN = accountIBAN;
//        this.interestRate = interestRate;
//        this.description = description;
//    }
//
//    @Override
//    public void accept(final TransactionVisitor visitor) {
//        visitor.visit(this);
//    }
//}
