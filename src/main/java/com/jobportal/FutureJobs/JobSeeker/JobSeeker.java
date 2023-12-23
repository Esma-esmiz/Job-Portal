package com.jobportal.FutureJobs.JobSeeker;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job-seeker")
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false, nullable = false)
    private Long id;

    @Valid
    @NotNull(message = "email not be empty")
    @NotEmpty(message = " email not be empty")
    @Email
    @Column(name = "email", unique = true)
    private  String email;

    @Valid
    @NotNull(message = "password not be empty")
    @NotEmpty(message = " password not be empty")
    @Pattern( regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
    message = "Has minimum 8 characters in length.\n" +
            "At least one uppercase English letter.\n" +
            "At least one lowercase English letter.\n" +
            "At least one digit.\n" +
            "At least one special character.")
    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime created_at;


    @Column(name = "last_modified")
    private  LocalDateTime last_modified;

    public JobSeeker(Long id, String email, String password, LocalDateTime created_at, LocalDateTime last_modified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public JobSeeker(String email, String password, LocalDateTime created_at, LocalDateTime last_modified) {
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public JobSeeker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }

    @Override
    public String toString() {
        return "JobSeeker{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created_at=" + created_at +
                ", last_modified=" + last_modified +
                '}';
    }
}
