package com.jobportal.FutureJobs.SubEmployer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "sub_employer")
public class SubEmployer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull(message = "name can not be empty")
    @Column(name = "name")
    private String name;
    @Valid
    @Email(message = "Invalid email")
    @Column(name = "email")
    private String email;
    @Valid
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Has minimum 8 characters in length.\n" +
                    "At least one uppercase English letter.\n" +
                    "At least one lowercase English letter.\n" +
                    "At least one digit.\n" +
                    "At least one special character.")
    @Column(name = "password")
    private String password;
    @Valid
    @NotNull(message = "account type can not be empty")
    @Column(name = "account_type")
    private String account_type;
    @Valid
    @NotEmpty(message = "department name can not be empty")
    @Column(name = "department_name")
    private String departmentName;
    @Valid
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Valid
    @Column(name = "last_modified")
    private LocalDateTime last_modified;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "user_id")
    private User employer;


    public SubEmployer() {
    }

    public SubEmployer(Long id, String name, String email, String password, String account_type, String departmentName, LocalDateTime created_at, LocalDateTime last_modified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.account_type = account_type;
        this.departmentName = departmentName;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }
}
