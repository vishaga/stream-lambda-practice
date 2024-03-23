package com.vishaga.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ElectoralBondBuyer(int serialNumber, String referenceNumber, LocalDate journalDate, LocalDate dateOfPurchase, LocalDate dateOfExpiry,
                                 String nameOfPurchaser, String uniqueBondNumber, Long amount, String issueBranchCode, String payTeller, String status) {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    public static ElectoralBondBuyer from(String line){
        String[] strings = line.split("\t");
        int serialNumber = Integer.parseInt(strings[0]);
        String referenceNumber = strings[1];
        LocalDate journalDate = LocalDate.parse(strings[2], formatter);
        LocalDate dateOfPurchase = LocalDate.parse(strings[3], formatter);
        LocalDate dateOfExpiry = LocalDate.parse(strings[4], formatter);
        String nameOfPurchaser = strings[5];
        String uniqueBondNumber = strings[6].concat(strings[7]);
        Long amount = Long.parseLong(strings[8].replace(",", ""));
        String issueBranchCode = strings[9];
        String issueTeller = strings[10];
        String status = strings[11];
        return new ElectoralBondBuyer(serialNumber, referenceNumber, journalDate, dateOfPurchase, dateOfExpiry, nameOfPurchaser, uniqueBondNumber, amount, issueBranchCode, issueTeller, status);
    }

    public static ElectoralBondBuyer fake() {
        LocalDate date = LocalDate.of(1947, 8, 15);
        return new ElectoralBondBuyer(0, "NA", date, date, date, "NA", "NA",  0L, "NA", "NA", "NA");
    }
}
