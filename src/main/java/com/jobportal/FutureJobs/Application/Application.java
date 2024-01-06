package com.jobportal.FutureJobs.Application;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job")
    private Job job;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_seeker_id")
    private User jobSeeker;
    @Column(name = "cover_letter")
    private String cover_letter;
    @Column(name = "status")
    private String status;

    @Column(name = "application_date")
    private LocalDateTime application_date;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    public Application() {
    }

    public Application(Long id, Job job, String cover_letter, LocalDateTime application_date) {
        this.id = id;
        this.job = job;
        this.cover_letter = cover_letter;
        this.application_date = application_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(User jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    public LocalDateTime getApplication_date() {
        return application_date;
    }

    public void setApplication_date(LocalDateTime application_date) {
        this.application_date = application_date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
