package com.jobportal.FutureJobs.User;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jobportal.FutureJobs.Application.Application;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Experience.Experience;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.JobSeeker.JobSeekerDetaile;
import com.jobportal.FutureJobs.Message.Message;
import com.jobportal.FutureJobs.SubEmployer.SubEmployer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull(message = "name not be empty")
    @NotEmpty(message = " name not be empty")
    @Length(min = 3, message = "name must be at list three character")
    @Column(name = "name", nullable = false)
    private String name;


    @Valid
    @NotNull(message = "email can not be empty")
    @NotEmpty(message = " email can not be empty")
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Valid
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Has minimum 8 characters in length.\n" + "At least one uppercase English letter.\n" + "At least one lowercase English letter.\n" + "At least one digit.\n" + "At least one special character.")
    @Column(name = "password", nullable = false)
    private String password;

    @Valid
    @NotNull(message = "phone number not be empty")
    @NotEmpty(message = " phone number not be empty")
    @Length(min = 10, message = "must be at list 10 numbers")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Valid
    @NotNull(message = "type not be empty")
    @NotEmpty(message = " type not be empty")
    @Column(name = "type", nullable = false)
    private String type;

    @Valid
    @Column(name = "tin", nullable = true)
    private String tin;

    @Valid
    @Column(name = "contact_name", nullable = true)
    private String contact_name;
    @Valid
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "jobseeker_detaile_id", nullable = true)
    private JobSeekerDetaile jobSeekerDetaile;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Job> job;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> application;

    @OneToMany(mappedBy = "employer")
    private List<SubEmployer> SubEmployer;

    @Column(name = "created_at")
    private LocalDateTime created_at;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachment;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "jobseeker_to_jobcategory", joinColumns = @JoinColumn(name = "jobseeker_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "jobcategory_id", referencedColumnName = "id"))
    private Set<JobCategory> jobCategory = new HashSet<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Message> fromUser;
    @JsonIgnore
    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> toUser;

    @OneToMany(mappedBy = "employer" )
    private List<SubEmployer> subEmployers;

    @Transient
    private List<Long> jobCategoryList;

    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    public User() {
    }


    public User(Long id, String name, String email, String password, String phone, String type, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.type = type;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", phone='" + phone + '\'' + ", type='" + type + '\'' + ", tin='" + tin + '\'' + ", contact_name='" + contact_name + '\'' + ", created_at=" + created_at + ", modified_at=" + modified_at + '}';
    }

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JobSeekerDetaile getJobSeekerDetaile() {
        return jobSeekerDetaile;
    }

    public void setJobSeekerDetaile(JobSeekerDetaile jobSeekerDetaile) {
        this.jobSeekerDetaile = jobSeekerDetaile;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }

    public List<Application> getApplication() {
        return application;
    }

    public void setApplication(List<Application> application) {
        this.application = application;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public Set<JobCategory> getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(Set<JobCategory> jobCategory) {
        this.jobCategory = jobCategory;
    }

    public List<Long> getJobCategoryList() {
        return jobCategoryList;
    }

    public void setJobCategoryList(List<Long> jobCategoryList) {
        this.jobCategoryList = jobCategoryList;
    }

    public List<Message> getFromUser() {
        return fromUser;
    }

    public void setFromUser(List<Message> fromUser) {
        this.fromUser = fromUser;
    }

    public List<Message> getToUser() {
        return toUser;
    }

    public void setToUser(List<Message> toUser) {
        this.toUser = toUser;
    }

    public List<com.jobportal.FutureJobs.SubEmployer.SubEmployer> getSubEmployer() {
        return SubEmployer;
    }

    public void setSubEmployer(List<com.jobportal.FutureJobs.SubEmployer.SubEmployer> subEmployer) {
        SubEmployer = subEmployer;
    }
}
