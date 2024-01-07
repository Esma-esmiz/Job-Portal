package com.jobportal.FutureJobs.Job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaile;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job-catagory")
public class JobCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @Length(min = 2, message = "must be at list two character")
    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified")
    private  LocalDateTime last_modified;

    @ManyToMany(mappedBy = "jobCategory", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "jobCategory")
    private List<User> jobSeeker;

    @OneToMany(mappedBy = "jobCategory", cascade = CascadeType.ALL)
    private List<Attachment> attachment;
    public JobCategory() {
    }

    public JobCategory(Long id, String title, LocalDateTime created_at, LocalDateTime last_modified) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public JobCategory(String title, LocalDateTime created_at, LocalDateTime last_modified) {
        this.title = title;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "JobCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", created_at=" + created_at +
                ", last_modified=" + last_modified +
                '}';
    }

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
