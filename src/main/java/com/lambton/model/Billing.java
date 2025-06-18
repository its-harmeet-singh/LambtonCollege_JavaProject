package com.lambton.model;

import java.time.LocalDate;

public class Billing {
    private int id;
    private int patientId;
    private double amount;
    private LocalDate billingDate;
    private String description;

    public Billing() { }

    public Billing(int patientId, double amount, LocalDate billingDate, String description) {
        this.patientId   = patientId;
        this.amount      = amount;
        this.billingDate = billingDate;
        this.description = description;
    }

    public Billing(int id, int patientId, double amount, LocalDate billingDate, String description) {
        this(patientId, amount, billingDate, description);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDate billingDate) { this.billingDate = billingDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
