package com.skypay.bankingservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountServiceImpl account;

    @BeforeEach
    public void setUp() {
        account = new AccountServiceImpl();
    }

    @Test
    @DisplayName("Test scénario d'acceptation complet")
    public void testAcceptanceScenario() {
        // Given
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        account.withdraw(500, LocalDate.of(2012, 1, 14));

        // When & Then
        assertEquals(2500, account.getBalance());
        assertEquals(3, account.getTransactionCount());

        // Affichage du relevé
        System.out.println("\n=== Test du scénario d'acceptation ===");
        account.printStatement();
    }

    @Test
    @DisplayName("Dépôt valide augmente le solde")
    public void testValidDeposit() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
        assertEquals(1, account.getTransactionCount());
    }

    @Test
    @DisplayName("Retrait valide diminue le solde")
    public void testValidWithdrawal() {
        account.deposit(1000);
        account.withdraw(300);
        assertEquals(700, account.getBalance());
        assertEquals(2, account.getTransactionCount());
    }

    @Test
    @DisplayName("Dépôt négatif lance une exception")
    public void testNegativeDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100);
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    @DisplayName("Dépôt de zéro lance une exception")
    public void testZeroDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0);
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    @DisplayName("Retrait négatif lance une exception")
    public void testNegativeWithdrawal() {
        account.deposit(1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-100);
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    @DisplayName("Retrait supérieur au solde lance une exception")
    public void testOverdraft() {
        account.deposit(500);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(600);
        });
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

    @Test
    @DisplayName("Retrait sur compte vide lance une exception")
    public void testWithdrawalFromEmptyAccount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(100);
        });
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

    @Test
    @DisplayName("Date future lance une exception")
    public void testFutureDate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(1000, futureDate);
        });
        assertTrue(exception.getMessage().contains("cannot be in the future"));
    }

    @Test
    @DisplayName("Date null lance une exception")
    public void testNullDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(1000, null);
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Plusieurs transactions en séquence")
    public void testMultipleTransactions() {
        account.deposit(1000);
        account.deposit(500);
        account.withdraw(200);
        account.deposit(300);
        account.withdraw(100);

        assertEquals(1500, account.getBalance());
        assertEquals(5, account.getTransactionCount());
    }

    @Test
    @DisplayName("Test de performance avec 10000 transactions")
    public void testPerformanceWith10000Transactions() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            account.deposit(100);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertEquals(1000000, account.getBalance());
        assertEquals(10000, account.getTransactionCount());

        System.out.println("\n=== Test de performance ===");
        System.out.println("10000 transactions effectuées en " + duration + "ms");
        assertTrue(duration < 5000, "Performance issue: took " + duration + "ms");
    }
}
