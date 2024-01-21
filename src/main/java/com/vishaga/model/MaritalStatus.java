package com.vishaga.model;

public enum MaritalStatus {
    MARRIED("Married"),
    UNMARRIED("Married"),
    SEPARATED("Separated"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed"),
    NEVER_MARRIED("Never married");

    final String status;

    MaritalStatus(String status){
        this.status = status;
    }
}
