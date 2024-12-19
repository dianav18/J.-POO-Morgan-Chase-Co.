---

# Bank Management System

## Overview

This project is a simulation of a bank management system that supports a
variety of operations such as managing accounts, handling transactions, and
generating reports. The application is developed in Java and showcases
object-oriented principles, with a focus on extensibility and code
organization.

---

## Key Features

1. **Account Management**:
    - Creation of classic and savings accounts.
    - Adding funds and setting minimum balances.
    - Associating and managing cards (including one-time payment cards).

2. **Transaction Handling**:
    - Online payments with card management.
    - Sending and receiving money between accounts.
    - Recording special transactions like insufficient funds or frozen cards.

3. **Reports**:
    - Spending reports that summarize user transactions and total spendings.
    - User and transaction details have a structured format.
    - Currency conversion for multi-currency transactions.
    - Card details and transaction history are displayed for reference.

4. **Bank Flow and Interactions**:
    - Users interact with the system to create accounts and perform operations.
    - Cards are created, used for payments, or deleted based on user actions.
    - Transactions include payments, transfers, and warnings.
    - Spending reports analyze transactions and group data by commerciants.
    - Currency conversion ensures multi-currency operations.

---

## Design Patterns Used

### Command Pattern
- The Command Pattern is a method where actions are treated as objects,
- making it easier to extend the code. (example: `AddFundsCommand`,
  `PayOnlineCommand`).
- Enables the execution of commands in a structured way.
- Provides the flexibility to add new commands with minimal changes to the
  existing system.

### Factory Pattern
- Used in `CommandLogicFactory` to instantiate commands dynamically based on
  user input.
- Simplifies the creation of complex objects.

### Visitor Pattern
- Implemented for transactions with the `TransactionVisitor` interface.
- Enables performing different operations (example: printing) on transactions
  without altering their structure.

---

## Class Hierarchy

1. **Transactions**:
    - A base `Transaction` class is extended by specific types (examples:
      `CardPaymentTransaction`, `SentTransaction`).
    - It has polymorphic behavior for different transaction types.

2. **Accounts**:
    - `Account` is the base class, extended by `ClassicAccount` and
      `SavingsAccount`.
    - Includes associated cards and a list of transactions.

3. **Handlers**:
    - Command-related classes like `CommandHandler` and `CommandInvoker`
      manage user inputs and actions.
    - `CurrencyConverter` handles multi-currency operations efficiently.
    - `AccountExtractor` and `TransactionExtractor` extract account and
      transaction details from the system.
    - 
---

## Bank Flow

1. **User Initialization**:
    - Users are added to the system with personal details and accounts.

2. **Account Operations**:
    - Users create accounts, deposit funds, and manage minimum balances.

3. **Card Management**:
    - Cards are created for accounts, supporting online payments.
    - One-time-use cards are destroyed after a transaction and replaced
      automatically.

4. **Transaction Handling**:
    - Transactions are recorded for payments, transfers, or
      warnings (examples: insufficient funds, frozen cards).

5. **Spending Analysis**:
    - Spending reports group user transactions by commerciants and analyze totals.
    - Reports provide a detailed view of user activity over a specific period.

---