package com.skypay.bankingservice.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Transaction(LocalDate date, int amount, int balance) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }
}
