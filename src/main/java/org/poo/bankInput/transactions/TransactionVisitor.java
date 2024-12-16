package org.poo.bankInput.transactions;

public interface TransactionVisitor {
    void visit(AccountCreatedTransaction transaction);
    void visit(SentTransaction transaction);
    void visit(CardCreatedTransaction transaction);
    void visit(CardPayment cardPayment);
    void visit(InsufficientFundsTransaction transaction);
    void visit(CardDestroyedTransaction cardDestroyedTransaction);
    void visit(CardFrozenTransaction cardFrozenTransaction);
    void visit(AccountWarningTransaction accountWaringTransaction);
    void visit(SplitPaymentTransaction splitPaymentTransaction);
    void visit(CommerciantTransaction commerciantTransaction);
    void visit(ReceivedTransaction receivedTransaction);
    void visit(CannotDeleteAccountTransaction cannotDeleteAccountTransaction);
    void visit(ChangeInterestRateTransaction changeInterestRateTransaction);
}
