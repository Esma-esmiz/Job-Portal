package com.jobportal.FutureJobs.JobSeeker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerDetaileRepository extends JpaRepository<JobSeekerDetaile, Long> {
}
