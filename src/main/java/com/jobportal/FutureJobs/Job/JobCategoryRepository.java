package com.jobportal.FutureJobs.Job;

import com.jobportal.FutureJobs.JobSeeker.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    @Query
    Optional<JobCategory> findJobCategoryByTitle(String title);
}
