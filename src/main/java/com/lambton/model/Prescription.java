package com.lambton.model;

import java.time.LocalDate;

public class Prescription {
    private int id;
    private int patientId;
    private Integer doctorId;     
    private String medicine;
    private String dosage;
    private LocalDate dateIssued;
    private String instructions;

    public Prescription() { }

    public Prescription(int patientId, Integer doctorId,
                        String medicine, String dosage,
                        LocalDate dateIssued, String instructions) {
        this.patientId    = patientId;
        this.doctorId     = doctorId;
        this.medicine     = medicine;
        this.dosage       = dosage;
        this.dateIssued   = dateIssued;
        this.instructions = instructions;
    }

    public Prescription(int id, int patientId, Integer doctorId,
                        String medicine, String dosage,
                        LocalDate dateIssued, String instructions) {
        this(patientId, doctorId, medicine, dosage, dateIssued, instructions);
        this.id = id;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }

    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public LocalDate getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDate dateIssued) { this.dateIssued = dateIssued; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
