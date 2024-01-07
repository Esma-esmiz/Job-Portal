package com.jobportal.FutureJobs.JobSeeker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.FutureJobs.Attachment.Attachment;
import com.jobportal.FutureJobs.Job.Job;
import com.jobportal.FutureJobs.Job.JobCategory;
import com.jobportal.FutureJobs.User.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job_seeker_detail")
public class JobSeekerDetaile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Valid
    @Column(name = "fname", nullable = true)
    private String fname;

    @Valid
    @Column(name = "lname", nullable = true)
    private String lname;

    @Valid
    @Column(name = "gender", nullable = true)
    private String gender;

    @Valid
    @Column(name = "phone", nullable = true)
    private String phone;

    @Valid
    @Column(name = "phone_optinal", nullable = true)
    private String phone_optinal;

    @Valid
    @Column(name = "country", nullable = true)
    private String country;

    @Valid
    @Column(name = "region", nullable = true)
    private String region;

    @Valid
    @Column(name = "city", nullable = true)
    private String city;

    @Valid
    @Column(name = "filed_of_study", nullable = true)
    private String filed_of_study;

    @Valid
    @Column(name = "higher_education_level", nullable = true)
    private String higher_education_level;

    @Valid
    @Column(name = "profession", nullable = true)
    private String profession;
    @Valid
    @Column(name = "career_level", nullable = true)
    private String career_level;
    @Valid
    @Column(name = "experience", nullable = true)
    private int experience;
    @Valid
    @Column(name = "cover_letter", nullable = true)
    private String cover_letter;
    @Valid
    @Column(name = "search_job_categories", nullable = true)
    private String search_job_categories;
    @Valid
    @Column(name = "job_modality", nullable = true)
    private String job_modality;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "modified_at")
    private  LocalDateTime modified_at;

    @OneToOne(mappedBy = "jobSeekerDetaile")
    private User user;

    @Transient
    private List<Long> jobCategoryList; // for accepting job-category ID




    public JobSeekerDetaile() {
    }

    public JobSeekerDetaile(Long id, String fname, String lname, String gender, String phone, String phone_optinal, String country, String region, String city, String filed_of_study, String higher_education_level, String profession, String career_level, int experience, String cover_letter,  String job_modality, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phone = phone;
        this.phone_optinal = phone_optinal;
        this.country = country;
        this.region = region;
        this.city = city;
        this.filed_of_study = filed_of_study;
        this.higher_education_level = higher_education_level;
        this.profession = profession;
        this.career_level = career_level;
        this.experience = experience;
        this.cover_letter = cover_letter;
        this.job_modality = job_modality;

        this.created_at = created_at;
        this.created_at=LocalDateTime.now();
        this.modified_at = modified_at;
    }



    @Override
    public String toString() {
        return "JobSeekerDetaile{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", phone_optinal='" + phone_optinal + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", filed_of_study='" + filed_of_study + '\'' +
                ", higher_education_level='" + higher_education_level + '\'' +
                ", profession='" + profession + '\'' +
                ", career_level='" + career_level + '\'' +
                ", experience=" + experience +
                ", cover_letter='" + cover_letter + '\'' +
                ", job_modality='" + job_modality + '\'' +
                ", created_at=" + created_at +
                ", modified_at=" + modified_at +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        this.created_at=LocalDateTime.now();
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_optinal() {
        return phone_optinal;
    }

    public void setPhone_optinal(String phone_optinal) {
        this.phone_optinal = phone_optinal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFiled_of_study() {
        return filed_of_study;
    }

    public void setFiled_of_study(String filed_of_study) {
        this.filed_of_study = filed_of_study;
    }

    public String getHigher_education_level() {
        return higher_education_level;
    }

    public void setHigher_education_level(String higher_education_level) {
        this.higher_education_level = higher_education_level;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCareer_level() {
        return career_level;
    }

    public void setCareer_level(String career_level) {
        this.career_level = career_level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    public String getSearch_job_categories() {
        return search_job_categories;
    }

    public void setSearch_job_categories(String search_job_categories) {
        this.search_job_categories = search_job_categories;
    }

    public String getJob_modality() {
        return job_modality;
    }

    public void setJob_modality(String job_modality) {
        this.job_modality = job_modality;
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

    public List<Long> getJobCategoryList() {
        return jobCategoryList;
    }

    public void setJobCategoryList(List<Long> jobCategoryList) {
        this.jobCategoryList = jobCategoryList;
    }
}
