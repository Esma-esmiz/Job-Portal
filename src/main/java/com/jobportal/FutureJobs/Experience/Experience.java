package com.jobportal.FutureJobs.Experience;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

            @JsonIgnore
            @ManyToOne
            @JoinColumn(name ="job_seeker_id")
            private User jobSeeker;

            @Valid
            @NotNull
            @Column(name ="employer_company")
            private String employer_company;

            @Valid
            @NotNull
            @Column(name ="job_title")
            private String job_title;

            @Valid
            @Past
            @Column(name ="start_date")
            private LocalDate start_date;

            @Valid
            @Past
            @Column(name ="end_date")
            private LocalDate end_date;

            @Valid
            @Column(name ="gross_salary")
            private int gross_salary;

            @Valid
            @Column(name ="skills")
            private String skills;

            @Valid
            @Column(name ="description")
            private String description;

    @Column(name = "created_at")
    private LocalDateTime created_at;


    @Column(name = "modified_at")
    private  LocalDateTime modified_at;

    public Experience() {
    }

    public Experience(Long id , String employer_company, String job_title, LocalDate start_date, LocalDate end_date, int gross_salary, String skills, String description) {
        this.id = id;

        this.employer_company = employer_company;
        this.job_title = job_title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.gross_salary = gross_salary;
        this.skills = skills;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(User jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getEmployer_company() {
        return employer_company;
    }

    public void setEmployer_company(String employer_company) {
        this.employer_company = employer_company;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getGross_salary() {
        return gross_salary;
    }

    public void setGross_salary(int gross_salary) {
        this.gross_salary = gross_salary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }
}
