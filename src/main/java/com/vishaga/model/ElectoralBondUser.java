package com.vishaga.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ElectoralBondUser(int serialNumber, LocalDate dateOfEncashment, String politicalPartyName, String accountNumber,
                                String uniqueBondNumber, Long amount, String payBranchCode, String payTeller) {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    public static ElectoralBondUser from(String line){
        String[] strings = line.split("\t");
        int serialNumber = Integer.parseInt(strings[0]);
        LocalDate dateOfEncashment = LocalDate.parse(strings[1], formatter);
        String politicalPartyName = strings[2];
        String accountNumber = strings[3];
        String uniqueBondNumber = strings[4].concat(strings[5]);
        Long amount = Long.parseLong(strings[6].replace(",", ""));
        String payBranchCode = strings[7];
        String payTeller = strings[8];
        return new ElectoralBondUser(serialNumber, dateOfEncashment, politicalPartyName, accountNumber, uniqueBondNumber, amount, payBranchCode, payTeller);
    }

    public static ElectoralBondUser fake() {
        return new ElectoralBondUser(0, LocalDate.of(1947, 8, 15), "NA", "NA", "NA", 0L, "NA", "NA");
    }
}
