package com.skypay.bankingservice;

import com.skypay.bankingservice.service.AccountService;
import com.skypay.bankingservice.service.AccountServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BankingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingServiceApplication.class, args);
        System.out.println("=== Banking Service Demo ===\n");

        AccountServiceImpl account = new AccountServiceImpl();

        try {
            // Scénario d'acceptation
            System.out.println("Executing acceptance scenario...\n");
            account.deposit(1000, LocalDate.of(2012, 1, 10));
            System.out.println(" Deposited 1000 on 10/01/2012");

            account.deposit(2000, LocalDate.of(2012, 1, 13));
            System.out.println(" Deposited 2000 on 13/01/2012");

            account.withdraw(500, LocalDate.of(2012, 1, 14));
            System.out.println(" Withdrew 500 on 14/01/2012");

            System.out.println("\nCurrent balance: " + account.getBalance());
            System.out.println("\n--- Bank Statement ---");
            account.printStatement();

            // Test des exceptions
            System.out.println("\n\n=== Testing Exception Handling ===\n");

            try {
                System.out.println("Attempting to deposit negative amount...");
                account.deposit(-100);
            } catch (IllegalArgumentException e) {
                System.out.println(" Exception caught: " + e.getMessage());
            }

            try {
                System.out.println("\nAttempting to withdraw more than balance...");
                account.withdraw(10000);
            } catch (IllegalArgumentException e) {
                System.out.println(" Exception caught: " + e.getMessage());
            }

            try {
                System.out.println("\nAttempting to use future date...");
                account.deposit(100, LocalDate.now().plusDays(1));
            } catch (IllegalArgumentException e) {
                System.out.println(" Exception caught: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
