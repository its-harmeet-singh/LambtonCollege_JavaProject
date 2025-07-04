package com.lambton.model;

import java.time.LocalDate;

public class Diagnosis {
    private int id;
    private int patientId;
    private Integer doctorId;
    private Integer appointmentId;
    private String diagnosis;
    private LocalDate dateDiagnosed;
    private String notes;

    public Diagnosis() { }

    public Diagnosis(int patientId, Integer doctorId, Integer appointmentId,
                     String diagnosis, LocalDate dateDiagnosed, String notes) {
        this.patientId     = patientId;
        this.doctorId      = doctorId;
        this.appointmentId = appointmentId;
        this.diagnosis     = diagnosis;
        this.dateDiagnosed = dateDiagnosed;
        this.notes         = notes;
    }

    public Diagnosis(int id, int patientId, Integer doctorId, Integer appointmentId,
                     String diagnosis, LocalDate dateDiagnosed, String notes) {
        this(patientId, doctorId, appointmentId, diagnosis, dateDiagnosed, notes);
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

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDate getDateDiagnosed() { return dateDiagnosed; }
    public void setDateDiagnosed(LocalDate dateDiagnosed) { this.dateDiagnosed = dateDiagnosed; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
