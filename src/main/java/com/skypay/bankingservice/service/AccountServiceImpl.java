package com.skypay.bankingservice.service;

import com.skypay.bankingservice.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class AccountServiceImpl implements AccountService  {
    @Getter
    private int balance;
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void deposit(int amount) {
        deposit(amount, LocalDate.now());
    }

    // Méthode surchargée pour les tests avec date personnalisée
    public void deposit(int amount, LocalDate date) {
        validateAmount(amount, "Deposit");
        validateDate(date);

        balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        withdraw(amount, LocalDate.now());
    }

    // Méthode surchargée pour les tests avec date personnalisée
    public void withdraw(int amount, LocalDate date) {
        validateAmount(amount, "Withdrawal");
        validateDate(date);

        if (amount > balance) {
            throw new IllegalArgumentException(
                    "Insufficient funds. Current balance: " + balance + ", Withdrawal amount: " + amount
            );
        }

        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount || Balance");

        // Affichage en ordre chronologique inverse
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            System.out.printf("%s || %d   || %d%n",
                    transaction.getFormattedDate(),
                    transaction.amount(),
                    transaction.balance()
            );
        }
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    private void validateAmount(int amount, String operationType) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    operationType + " amount must be positive. Provided: " + amount
            );
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Transaction date cannot be null");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Transaction date cannot be in the future: " + date
            );
        }
    }
}
