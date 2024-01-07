package com.jobportal.FutureJobs.Attachment;

import com.jobportal.FutureJobs.Application.Application;
import com.jobportal.FutureJobs.Experience.Experience;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaile;
import com.jobportal.FutureJobs.Message.Message;
import com.jobportal.FutureJobs.SubEmployer.SubEmployer;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Valid
    @Column(name = "name")
    private String name;
    @Valid
    @NotNull(message = "type not be empty")
    @Column(name = "type")
    private String type;
    @Valid
    @NotNull(message = "file type not be empty")
    @Column(name = "file_type")
    private String file_type;
    @Valid
    @NotNull(message = "file path not be empty")
    @Column(name = "path")
    private String path;

    @Valid
    @NotNull(message = "status name not be empty")
    @Column(name = "status")
    private String status;


    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application")
    private Application application;
                @ManyToOne(cascade = CascadeType.ALL)
            @JoinColumn(name = "experience")
            private Experience experience;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Job job;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job-category")
    private JobCategory jobCategory;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobseeker_detail")
    private JobSeekerDetaile jobseeker_detail;
//            @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "message") private Message message;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_employer")
    private SubEmployer sub_employer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message")
    private Message message;


    public Attachment() {
    }

    public Attachment(Long id, String name, String type, String file_type, String path, String status, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.file_type = file_type;
        this.path = path;
        this.status = status;
        this.created_at = created_at;
        this.modified_at = modified_at;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    public JobSeekerDetaile getJobseeker_detail() {
        return jobseeker_detail;
    }

    public void setJobseeker_detail(JobSeekerDetaile jobseeker_detail) {
        this.jobseeker_detail = jobseeker_detail;
    }

    public SubEmployer getSub_employer() {
        return sub_employer;
    }

    public void setSub_employer(SubEmployer sub_employer) {
        this.sub_employer = sub_employer;
    }
}
