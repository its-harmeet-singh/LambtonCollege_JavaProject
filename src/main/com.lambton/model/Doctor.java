package main.com.lambton.model;

public class Doctor {
    private int id;
    private String name;
    private String specialty;
    private String phone;
    private String email;

    public Doctor() { }

    public Doctor(String name, String specialty, String phone, String email) {
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
    }

    public Doctor(int id, String name, String specialty, String phone, String email) {
        this(name, specialty, phone, email);
        this.id = id;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
