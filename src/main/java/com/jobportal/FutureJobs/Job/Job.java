package com.jobportal.FutureJobs.Job;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jobportal.FutureJobs.Application.Application;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Valid
    @NotNull(message = "title not be empty")
    @NotEmpty(message = " title not be empty")
    @Length(min = 3, message = "title must be at list three character")
    @Column(name = "title", nullable = false)
    private String title;

    @Valid
    @NotNull(message = "vacancy ID not be empty")
    @NotEmpty(message = " vacancy ID not be empty")
    @Column(name = "vacancy_id", nullable = false)
    private String vacancy_id;

    @Valid
//    @NotNull(message = "Post Date not be empty")
//    @NotEmpty(message = " Post Date not be empty")
//    @FutureOrPresent(message = "Post date must be today or in future")
    @Column(name = "post_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime post_date;

    @Valid
//    @NotNull(message = "deadline Date not be empty")
//    @NotEmpty(message = " deadline Date not be empty")
//    @Future(message = "deadline date must be in future")
    @Column(name = "deadline", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime deadline;

    @Valid
    @NotNull(message = "min experience not be empty")
    @PositiveOrZero(message = "min experience must be zero or positive number ")
    @Column(name = "min_experiance", nullable = false)
    private int min_experiance;
    @Valid
    @Positive(message = "max experience must be positive number ")
    @Column(name = "max_experiance", nullable = false)
    private int max_experiance;
    @Valid
    @Column(name = "description", nullable = false)
    private String description;
    @Valid
    @Column(name = "work_place", nullable = false)
    private String work_place;
    @Valid
    @Column(name = "job_modality", nullable = false)
    private String job_modality;

    @Valid
    @Column(name = "applied_through",columnDefinition = " default 'Future Jobs'", nullable = true )
    private  String applied_through;
    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employer")
    private User employer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Application> application;

    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Attachment> attachment;
    @Transient
    private List<Long> jobCategoryList;
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "job-jobcategory",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_category_id", referencedColumnName = "id"))
    private Set<JobCategory> jobCategory = new HashSet<>();


    public Job() {
    }

    public Job(Long id, String title, String vacancy_id, LocalDateTime post_date, LocalDateTime deadline, int min_experiance, int max_experiance, String description, String work_place, String job_modality, String applied_through, String status, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.title = title;
        this.vacancy_id = vacancy_id;
        this.post_date = post_date;
        this.deadline = deadline;
        this.min_experiance = min_experiance;
        this.max_experiance = max_experiance;
        this.description = description;
        this.work_place = work_place;
        this.job_modality = job_modality;
        this.applied_through = applied_through;
        this.status = status;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", vacancy_id='" + vacancy_id + '\'' +
                ", post_date=" + post_date +
                ", deadline=" + deadline +
                ", min_experiance=" + min_experiance +
                ", max_experiance=" + max_experiance +
                ", description='" + description + '\'' +
                ", work_place='" + work_place + '\'' +
                ", job_modality='" + job_modality + '\'' +
                ", created_at=" + created_at +
                ", modified_at=" + modified_at +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVacancy_id() {
        return vacancy_id;
    }

    public void setVacancy_id(String vacancy_id) {
        this.vacancy_id = vacancy_id;
    }

    public LocalDateTime getPost_date() {
        return post_date;
    }

    public void setPost_date(LocalDateTime post_date) {
        this.post_date = post_date;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getMin_experiance() {
        return min_experiance;
    }

    public void setMin_experiance(int min_experiance) {
        this.min_experiance = min_experiance;
    }

    public int getMax_experiance() {
        return max_experiance;
    }

    public void setMax_experiance(int max_experiance) {
        this.max_experiance = max_experiance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public String getJob_modality() {
        return job_modality;
    }

    public void setJob_modality(String job_modality) {
        this.job_modality = job_modality;
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

    public List<Application> getApplication() {
        return application;
    }

    public void setApplication(List<Application> application) {
        this.application = application;
    }

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
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

    public String getApplied_through() {
        return applied_through;
    }

    public void setApplied_through(String applied_through) {
        this.applied_through = applied_through;
    }
}
