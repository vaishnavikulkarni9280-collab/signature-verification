package com.example.demo.auth.models;

import com.example.demo.auth.security.EncryptionUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column
    private String department;

    @Column
    private String year;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String category; // Student, Warden, etc.

    // ✅ Securely store signature image as binary data (BLOB)
    @Lob
    @Column(name = "signature_image", columnDefinition = "LONGBLOB")
    private byte[] signatureImage;

    // 🔹 Default constructor
    public User() {}

    // 🔹 Parameterized constructor
    public User(String name, String email, String mobile, String department, String year,
                String password, String category) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.department = department;
        this.year = year;
        this.password = password;
        this.category = category;
    }

    // ---------- Getters and Setters ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public byte[] getSignatureImage() {
        try {
            return EncryptionUtil.decryptBytes(signatureImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSignatureImage(byte[] signatureImage) {
        try {
            this.signatureImage = EncryptionUtil.encryptBytes(signatureImage);
        } catch (Exception e) {
            e.printStackTrace();
            this.signatureImage = null;
        }
    }
}
