package com.lambton.model;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentTime;
    private String reason;

    public Appointment() { }

    public Appointment(int patientId, int doctorId,
                       LocalDateTime appointmentTime, String reason) {
        this.patientId       = patientId;
        this.doctorId        = doctorId;
        this.appointmentTime = appointmentTime;
        this.reason          = reason;
    }

    public Appointment(int id, int patientId, int doctorId,
                       LocalDateTime appointmentTime, String reason) {
        this(patientId, doctorId, appointmentTime, reason);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
