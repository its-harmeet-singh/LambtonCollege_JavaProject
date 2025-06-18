package com.lambton.model;

import java.time.LocalDate;

public class Prescription {
    private int id;
    private int patientId;
    private Integer doctorId;
    private Integer appointmentId;
    private String medicine;
    private String dosage;
    private LocalDate dateIssued;
    private String instructions;

    public Prescription() { }

    public Prescription(int patientId, Integer doctorId, Integer appointmentId,
                        String medicine, String dosage,
                        LocalDate dateIssued, String instructions) {
        this.patientId     = patientId;
        this.doctorId      = doctorId;
        this.appointmentId = appointmentId;
        this.medicine      = medicine;
        this.dosage        = dosage;
        this.dateIssued    = dateIssued;
        this.instructions  = instructions;
    }

    public Prescription(int id, int patientId, Integer doctorId, Integer appointmentId,
                        String medicine, String dosage,
                        LocalDate dateIssued, String instructions) {
        this(patientId, doctorId, appointmentId, medicine, dosage, dateIssued, instructions);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }

    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }

    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public LocalDate getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDate dateIssued) { this.dateIssued = dateIssued; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
